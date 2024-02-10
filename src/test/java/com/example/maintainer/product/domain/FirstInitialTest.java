package com.example.maintainer.product.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FirstInitialTest {

  @ParameterizedTest
  @CsvSource(
      {
          "네이버,ㄴㅇㅂ",
          "슈크림,ㅅㅋㄹ",
          "라때,ㄹㄸ",
          "슈크림 라때,ㅅㅋㄹ ㄹㄸ"
      }
  )
  void 한글초성을_반환한다(String value, String expect) {
    Assertions.assertThat(new FirstInitial(value).getValue()).isEqualTo(expect);
  }

  @ParameterizedTest
  @CsvSource(
      value = {
          "icecream",
          "庫",
          "おげんきですか。",
          "你吃饭了吗"
      }
  )
  void 한글이_아닌_언어는_null을_반환한다(String value) {
    Assertions.assertThat(new FirstInitial(value).getValue()).isNull();
  }
}