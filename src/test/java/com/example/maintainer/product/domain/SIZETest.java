package com.example.maintainer.product.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SIZETest {

  @ParameterizedTest
  @CsvSource(
      {
          "small",
          "large",
      }
  )
  void 해당사이즈는_매칭되어야한다(String size) {
    Assertions.assertThatCode(() -> SIZE.getMatchSize(size)).doesNotThrowAnyException();
  }

  @ParameterizedTest
  @CsvSource(
      {
          "Small",
          "Large",
          "medium",
          "Medium"
      }
  )
  void 해당케이스들은_예외를_반환한다(String size) {
    Assertions.assertThatCode(() -> SIZE.getMatchSize(size))
        .isExactlyInstanceOf(IllegalSizeException.class);
  }
}