package com.example.maintainer.product.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.maintainer.ControllerTestMustExtends;
import com.example.maintainer.product.domain.ProductSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

  void 로그인후_상품등록(String token) throws Exception {
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
    api호출(상품저장(product, token));
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

  private String 회원가입로그인성공후토큰반환(String phoneNumber) throws Exception {
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

  private RequestBuilder 상품저장(String request, String token) throws JsonProcessingException {
    return post("/product")
        .header("X-USER-ID", token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request);
  }

  private RequestBuilder 상품커서조회(long cursorId, String token) {
    return get("/product/list")
        .param("cursorId", String.valueOf(cursorId))
        .header("X-USER-ID", token);
  }

  private List<ProductSearch> 상품커서조회결과(MockHttpServletResponse response) throws Exception {
    return objectMapper.readValue(
        response.getContentAsString(),
        new TypeReference<CustomResponse<ProductSearchWithNextCursorIdResponse>>() {
        }).getData().productSearches();
  }

  @Nested
  class 상품저장 {

    @Test
    void 사장님이_로그인하지않은경우_상품은_저장되지_않는다() throws Exception {
      String product = "{\n"
          + "    \"category\":\"asdf\",\n"
          + "    \"salePrice\":1234,\n"
          + "    \"cost\": 500,\n"
          + "    \"name\":\"test\",\n"
          + "    \"description\":\"test\",\n"
          + "    \"barcode\":12341234,\n"
          + "    \"size\":\"small\",\n"
          + "    \"expiredDate\":\"2023-02-12T00:00:00\"\n"
          + "}";
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
  }

  @Nested
  class 커서조회 {

    @Test
    void 사장님이_로그인하지_않은경우_상품은_커서_조회되지_않는다() throws Exception {
      String canUseToken = 회원가입로그인성공후토큰반환();
      for (int i = 0; i < 15; i++) {
        로그인후_상품등록(canUseToken);
      }
      String token = 만기된토큰;
      MockHttpServletResponse response = api호출(상품커서조회(0L, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @CsvSource({
        "1,1",
        "2,2",
        "3,3",
        "4,4",
        "5,5",
        "6,6",
        "7,7",
        "8,8",
        "9,9",
        "10,10",
        "11,10",
        "12,10",
        "13,10",
        "14,10"
    })
    void 사장님이_로그인한_경우_상품은_한번에_10개까지_조회_할수_있다(int length, int expectSize) throws Exception {
      String canUseToken = 회원가입로그인성공후토큰반환();
      for (int i = 0; i < length; i++) {
        로그인후_상품등록(canUseToken);
      }
      String token = canUseToken;
      MockHttpServletResponse response = api호출(상품커서조회(0L, token));
      List<ProductSearch> result = 상품커서조회결과(response);

      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
      Assertions.assertThat(result.size()).isEqualTo(expectSize);
    }

    @Test
    void 사장님이_로그인한_경우라도_상품갯수가_0개면_NO_CONTENT를_반환한다() throws Exception {
      String canUseToken = 회원가입로그인성공후토큰반환();
      String token = canUseToken;
      MockHttpServletResponse response = api호출(상품커서조회(0L, token));

      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @ParameterizedTest
    @CsvSource({
        "1,1,1",
        "2,1,1",
        "3,1,1",
        "4,1,1",
        "5,1,1",
        "2,2,2",
        "3,2,2",
        "4,2,2",
        "5,2,2",
        "4,3,3",
        "5,3,3",
        "5,4,4",
    })
    void 사장님이_로그인한_경우라도_다른사장님의_상품은_조회되지않는다(int otherCreate, int iCreate, int expectSize)
        throws Exception {
      String canUseToken = 회원가입로그인성공후토큰반환();
      for (int i = 0; i < otherCreate; i++) {
        로그인후_상품등록(canUseToken);
      }
      String token = 회원가입로그인성공후토큰반환("010-7890-7890");
      for (int i = 0; i < iCreate; i++) {
        로그인후_상품등록(token);
      }
      MockHttpServletResponse response = api호출(상품커서조회(0L, token));
      List<ProductSearch> result = 상품커서조회결과(response);

      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
      Assertions.assertThat(result.size()).isEqualTo(expectSize);
    }
  }
}