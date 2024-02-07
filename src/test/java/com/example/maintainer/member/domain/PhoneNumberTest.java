package com.example.maintainer.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PhoneNumberTest {

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
  void 해당케이스들은_휴대폰번호가_아니다(String data) {
    Assertions.assertThatCode(() -> new PhoneNumber(data))
        .isExactlyInstanceOf(IllegalPhoneNumberException.class);
  }

  @ParameterizedTest
  @CsvSource(value = {
      "01012344567",
      "010-1234-1234"
  }, nullValues = "null")
  void 해당케이스들은_휴대폰번호이다(String data) {
    Assertions.assertThatCode(() -> new PhoneNumber(data))
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @CsvSource(value = {
      "01012344567,01012344567",
      "010-1234-1234,01012341234"
  }, nullValues = "null")
  void 휴대폰번호는_숫자만_나와야한다(String phoneNumber, String expect) {
    Assertions.assertThat(new PhoneNumber(phoneNumber).getPhoneNumber()).isEqualTo(expect);
  }

}