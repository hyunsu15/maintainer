package com.example.maintainer.product.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.maintainer.ControllerTestMustExtends;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.RequestBuilder;

class ProductControllerTest extends ControllerTestMustExtends {

  private static final String 만기된토큰 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTAxMjM0MTIzNCIsImlhdCI6MTcwNzM4MzU4NywiZXhwIjoxNzA3NDY5OTg3fQ.QxOWWl7c9SEPTSYNZfF4XvE7K8cDKHU9yL5uTINR1DrvxD9evgaSLG4xCi4yP0ZulI0M8FK2VorCAZb_tLTCMQ";

  @BeforeEach
  protected void setup() {
    super.setUp();
  }

  @Test
  void 사장님이_로그인하지않은경우_상품은_저장되지_않는다() throws Exception {
    String product = "{\n"
        + "      \"category\":\"asdf\",\n"
        + "        \"salePrice\":1234,\n"
        + "        \"cost\": 500,\n"
        + "        \"name\":\"test\",\n"
        + "        \"description\":\"test\",\n"
        + "        \"barcode\":12341234,\n"
        + "        \"size\":\"small\",\n"
        + "        \"expiredDate\":\"2023-02-12T00:00:00\"\n"
        + "    }";
    String token = 만기된토큰;
    MockHttpServletResponse response = api호출(상품저장(product, token));
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  void 사장님이_로그인한_경우_상품은_저장된다() throws Exception {
    String product = "{\n"
        + "      \"category\":\"asdf\",\n"
        + "        \"salePrice\":1234,\n"
        + "        \"cost\": 500,\n"
        + "        \"name\":\"test\",\n"
        + "        \"description\":\"test\",\n"
        + "        \"barcode\":12341234,\n"
        + "        \"size\":\"small\",\n"
        + "        \"expiredDate\":\"2023-02-12T00:00:00\"\n"
        + "    }";
    String token = 회원가입로그인성공후토큰반환();
    MockHttpServletResponse response = api호출(상품저장(product, token));
    Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  private String 회원가입로그인성공후토큰반환() throws Exception {
    String phoneNumber = "010-1234-1234";
    String password = "1234";

    api호출(회원가입(phoneNumber, password));
    return objectMapper.readValue(
            api호출(로그인(phoneNumber, password)).getContentAsString(),
            new TypeReference<CustomResponse<String>>() {
            })
        .getData();
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

  private RequestBuilder 상품저장(ProductCreateRequest request, String token)
      throws JsonProcessingException {
    return post("/product")
        .header("X-USER-ID", token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request));
  }

  private RequestBuilder 상품저장(String request, String token) throws JsonProcessingException {
    return post("/product")
        .header("X-USER-ID", token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request);
  }
}