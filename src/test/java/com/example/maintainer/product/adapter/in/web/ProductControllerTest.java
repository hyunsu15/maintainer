package com.example.maintainer.product.adapter.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
        + "        \"name\":\"슈크림 라때\",\n"
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

  private RequestBuilder 로그아웃(String token) throws JsonProcessingException {
    return post("/member/sign/out")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new SignOutRequest(token)));
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

  private List<ProductSearch> 상품커서조회결과(String token) throws Exception {
    MockHttpServletResponse response = api호출(상품커서조회(0L, token));
    return objectMapper.readValue(
        response.getContentAsString(),
        new TypeReference<CustomResponse<ProductSearchWithNextCursorIdResponse>>() {
        }).getData().productSearches();
  }

  private RequestBuilder 상품업데이트(String request, Long id, String token) {
    return patch("/product/{id}", id)
        .header("X-USER-ID", token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request);
  }

  private RequestBuilder 상품삭제(Long id, String token) {
    return delete("/product/{id}", id)
        .header("X-USER-ID", token);
  }

  private RequestBuilder 상품상세조회(Long id, String token) {
    return get("/product/{id}", id)
        .header("X-USER-ID", token);
  }

  private RequestBuilder 상품이름검색(String searchValue, String token) {
    return get("/product/search")
        .param("searchValue", searchValue)
        .header("X-USER-ID", token);
  }

  @Nested
  class 상품이름검색 {

    String searchValue = "슈크림";

    @Test
    void 로그아웃된_토큰일경우__상품은_검색되지_않는다() throws Exception {
      String token = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(token);
      api호출(로그아웃(token));

      MockHttpServletResponse response = api호출(상품이름검색(searchValue, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 유효하지않은_토큰일경우_상품은_검색되지_않는다() throws Exception {
      String canUseToken = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(canUseToken);

      String token = 만기된토큰;

      MockHttpServletResponse response = api호출(상품이름검색(searchValue, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 사장님이_로그인한_경우라도_상품이_없으면_검색되지_않는다() throws Exception {
      String canUseToken = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(canUseToken);

      String token = canUseToken;
      String searchValue = "없는이름";

      MockHttpServletResponse response = api호출(상품이름검색(searchValue, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 사장님이_로그인한_경우라도_남의상품은_조회되지_않는다() throws Exception {
      String otherToken = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(otherToken);
      String token = 회원가입로그인성공후토큰반환("010-7894-7894");

      MockHttpServletResponse response = api호출(상품이름검색(searchValue, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @ParameterizedTest
    @CsvSource({
        "슈크림",
        "크림",
        "ㅅㅋㄹ",
        "ㄹㄸ"
    })
    void 사장님이_로그인한_경우_자신의_상품은_검색된다(String searchValue) throws Exception {
      String token = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(token);

      MockHttpServletResponse response = api호출(상품이름검색(searchValue, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
  }

  @Nested
  class 상품상세내역 {

    @Test
    void 로그아웃된_토큰일경우_상품은_조회되지_않는다() throws Exception {
      String token = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(token);

      Long productId = 상품커서조회결과(token).get(0).id();
      api호출(로그아웃(token));

      MockHttpServletResponse response = api호출(상품상세조회(productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 유효하지않은_토큰일경우_상품은_조회되지_않는다() throws Exception {
      String canUseToken = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(canUseToken);

      String token = 만기된토큰;
      Long productId = 상품커서조회결과(canUseToken).get(0).id();

      MockHttpServletResponse response = api호출(상품상세조회(productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 사장님이_로그인한_경우라도_상품이_없으면_조회되지_않는다() throws Exception {
      String canUseToken = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(canUseToken);

      String token = 만기된토큰;
      Long productId = Long.MIN_VALUE;

      MockHttpServletResponse response = api호출(상품상세조회(productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 사장님이_로그인한_경우라도_남의상품은_조회되지_않는다() throws Exception {
      String otherToken = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(otherToken);
      Long productId = 상품커서조회결과(otherToken).get(0).id();
      String token = 회원가입로그인성공후토큰반환("010-7894-7894");

      MockHttpServletResponse response = api호출(상품상세조회(productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 사장님이_로그인한_경우_자신의_상품은_조회된다() throws Exception {
      String token = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(token);
      Long productId = 상품커서조회결과(token).get(0).id();

      MockHttpServletResponse response = api호출(상품상세조회(productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
  }

  @Nested
  class 상품삭제 {

    @Test
    void 로그아웃된_토큰일경우_상품은_삭제되지_않는다() throws Exception {
      String token = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(token);
      Long productId = 상품커서조회결과(token).get(0).id();
      api호출(로그아웃(token));

      MockHttpServletResponse response = api호출(상품삭제(productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 유효하지않은_토큰일경우_상품은_삭제되지_않는다() throws Exception {
      String canUseToken = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(canUseToken);

      String token = 만기된토큰;
      Long productId = 상품커서조회결과(canUseToken).get(0).id();

      MockHttpServletResponse response = api호출(상품삭제(productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 사장님이_로그인한_경우라도_상품이_없으면_삭제되지_않는다() throws Exception {
      String canUseToken = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(canUseToken);

      String token = 만기된토큰;
      Long productId = Long.MIN_VALUE;

      MockHttpServletResponse response = api호출(상품삭제(productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 사장님이_로그인한_경우라도_남의상품은_삭제되지_않는다() throws Exception {
      String otherToken = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(otherToken);
      Long productId = 상품커서조회결과(otherToken).get(0).id();
      String token = 회원가입로그인성공후토큰반환("010-7894-7894");

      MockHttpServletResponse response = api호출(상품삭제(productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 사장님이_로그인한_경우_자신의_상품은_삭제된다() throws Exception {
      String token = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(token);
      Long productId = 상품커서조회결과(token).get(0).id();

      MockHttpServletResponse response = api호출(상품삭제(productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
  }

  @Nested
  class 상품업데이트 {

    String product = "{\n"
        + "      \"category\":\"asdf\",\n"
        + "        \"salePrice\":1234,\n"
        + "        \"name\":\"test\",\n"
        + "        \"description\":\"123\",\n"
        + "        \"barcode\":123000,\n"
        + "        \"size\":\"large\",\n"
        + "        \"expiredDate\":\"2023-02-12T00:00:00\"\n"
        + "    }";

    @Test
    void 로그아웃된_토큰일경우_상품은_업데이트되지_않는다() throws Exception {
      String token = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(token);
      Long productId = 상품커서조회결과(token).get(0).id();
      api호출(로그아웃(token));

      MockHttpServletResponse response = api호출(상품업데이트(product, productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 유효하지않은_토큰일경우_상품은_업데이트되지_않는다() throws Exception {
      String canUseToken = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(canUseToken);

      String token = 만기된토큰;
      Long productId = 상품커서조회결과(canUseToken).get(0).id();

      MockHttpServletResponse response = api호출(상품업데이트(product, productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 사장님이_로그인한_경우라도_상품이_없으면_업데이트되지_않는다() throws Exception {
      String token = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(token);
      Long productId = Long.MIN_VALUE;

      MockHttpServletResponse response = api호출(상품업데이트(product, productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 사장님이_로그인한_경우라도_남의상품은_업데이트되지_않는다() throws Exception {
      String otherToken = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(otherToken);
      Long productId = 상품커서조회결과(otherToken).get(0).id();
      String token = 회원가입로그인성공후토큰반환("010-7894-7894");

      MockHttpServletResponse response = api호출(상품업데이트(product, productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 사장님이_로그인한_경우_자신의_상품은_업데이트된다() throws Exception {
      String token = 회원가입로그인성공후토큰반환();
      로그인후_상품등록(token);
      Long productId = 상품커서조회결과(token).get(0).id();

      MockHttpServletResponse response = api호출(상품업데이트(product, productId, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
  }

  @Nested
  class 상품저장 {

    private final String product = "{\n"
        + "      \"category\":\"asdf\",\n"
        + "        \"salePrice\":1234,\n"
        + "        \"cost\": 500,\n"
        + "        \"name\":\"test\",\n"
        + "        \"description\":\"test\",\n"
        + "        \"barcode\":12341234,\n"
        + "        \"size\":\"small\",\n"
        + "        \"expiredDate\":\"2023-02-12T00:00:00\"\n"
        + "    }";

    @Test
    void 로그아웃된_토큰일경우_상품은_저장되지_않는다() throws Exception {
      String token = 회원가입로그인성공후토큰반환();
      api호출(로그아웃(token));
      MockHttpServletResponse response = api호출(상품저장(product, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 유효하지않은_토큰일경우_상품은_저장되지_않는다() throws Exception {
      String token = 만기된토큰;
      MockHttpServletResponse response = api호출(상품저장(product, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 사장님이_로그인한_경우_상품은_저장된다() throws Exception {

      String token = 회원가입로그인성공후토큰반환();
      MockHttpServletResponse response = api호출(상품저장(product, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
  }

  @Nested
  class 커서조회 {

    @Test
    void 로그아웃된_토큰일경우_상품은_커서_조회되지_않는다() throws Exception {
      String token = 회원가입로그인성공후토큰반환();
      for (int i = 0; i < 15; i++) {
        로그인후_상품등록(token);
      }
      api호출(로그아웃(token));
      MockHttpServletResponse response = api호출(상품커서조회(0L, token));
      Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 유효하지않은_토큰일경우_상품은_커서_조회되지_않는다() throws Exception {
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
    void 사장님이_로그인한_경우_자신의_상품을_한번에_10개까지_조회_할수_있다(int length, int expectSize) throws Exception {
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