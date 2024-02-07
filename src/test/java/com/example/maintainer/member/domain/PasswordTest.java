package com.example.maintainer.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PasswordTest {

  @ParameterizedTest
  @CsvSource({
      "1234,03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4",
      "123456789,15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225"
  })
  void 패스워드는_암호화되어야한다(String password, String expect) {
    Assertions.assertThat(new Password(password).getPassword()).isEqualTo(expect);
  }
}