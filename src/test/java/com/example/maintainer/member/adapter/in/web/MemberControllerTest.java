package com.example.maintainer.member.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.maintainer.ControllerTestMustExtends;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.RequestBuilder;

class MemberControllerTest extends ControllerTestMustExtends {

  private final static String DEFAULT_PASSWORD = "1234";

  @BeforeEach
  protected void setUp() {
    super.setUp();
  }

  @Test
  void 폰번호가_이미등록되있으면_회원가입이_되지_않는다() throws Exception {
    api호출(회원가입("010-1234-1234", DEFAULT_PASSWORD));
    MockHttpServletResponse response = api호출(회원가입("010-1234-1234", DEFAULT_PASSWORD));
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @ParameterizedTest
  @CsvSource(value = {
      "010",
      "qwe",
      "null",
      "010123445678",
      "01-0123-44567",
      "010-1234-4567-",
      "-010-1234-4567",
      "123-4567-8901",
      "010-1234-123-"
  }, nullValues = "null")
  void 폰번호가_이상하면_회원가입이_되지_않는다(String phoneNumber) throws Exception {
    MockHttpServletResponse response = api호출(회원가입(phoneNumber, DEFAULT_PASSWORD));
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @ParameterizedTest
  @CsvSource(value = {
      "01012344567",
      "010-1234-1234"
  })
  void 폰번호가_정상이고가입되지않은번호라면_회원가입이_된다(String phoneNumber) throws Exception {
    MockHttpServletResponse response = api호출(회원가입(phoneNumber, DEFAULT_PASSWORD));
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  private RequestBuilder 회원가입(String phoneNumber, String password) throws JsonProcessingException {
    return post("/member/sign/up")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new SignInRequest(phoneNumber, password)));
  }
}