package com.example.maintainer.member.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.maintainer.ControllerTestMustExtends;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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


  @ParameterizedTest
  @CsvSource(value = {
      "01012344567,1234",
      "010-1234-1234,123"
  })
  void 폰번호가_틀리거나_비밀번호가틀리면_로그인_되지_않는다(String phoneNumber, String password) throws Exception {
    api호출(회원가입("010-1234-1234", DEFAULT_PASSWORD));

    MockHttpServletResponse response = api호출(로그인(phoneNumber, password));
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void 폰번호와비밀번호가일치하면_로그인_된다() throws Exception {
    String phoneNumber = "010-1234-1234";
    api호출(회원가입(phoneNumber, DEFAULT_PASSWORD));

    MockHttpServletResponse response = api호출(로그인(phoneNumber, DEFAULT_PASSWORD));
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @ParameterizedTest
  @CsvSource(value = {
      "qwe",
      "1234",
      "토큰토큰",
      "토큰 토큰",
      "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTAxMjM0MTIzNCIsImlhdCI6MTcwNzM4MzU4NywiZXhwIjoxNzA3NDY5OTg3fQ.QxOWWl7c9SEPTSYNZfF4XvE7K8cDKHU9yL5uTINR1DrvxD9evgaSLG4xCi4yP0ZulI0M8FK2VorCAZb_tLTCMQ"
  })
  void 유효하지않은_토큰이아닌경우_로그아웃이_되지않는다(String token) throws Exception {
    MockHttpServletResponse response = api호출(로그아웃(token));
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void 유효한_토큰일경우_로그아웃이_된다() throws Exception {
    String phoneNumber = "010-1234-1234";
    api호출(회원가입(phoneNumber, DEFAULT_PASSWORD));

    String token = objectMapper.readValue(
            api호출(로그인(phoneNumber, DEFAULT_PASSWORD)).getContentAsString(),
            new TypeReference<CustomResponse<String>>() {
            })
        .getData();
    MockHttpServletResponse response = api호출(로그아웃(token));
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  private RequestBuilder 회원가입(String phoneNumber, String password) throws JsonProcessingException {
    return post("/member/sign/up")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new SignUpRequest(phoneNumber, password)));
  }

  private RequestBuilder 로그인(String phoneNumber, String password) throws JsonProcessingException {
    return post("/member/sign/in")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new SignInRequest(phoneNumber, password)));
  }

  private RequestBuilder 로그아웃(String token) throws Exception {
    return post("/member/sign/out")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new SignOutRequest(token)));
  }
}