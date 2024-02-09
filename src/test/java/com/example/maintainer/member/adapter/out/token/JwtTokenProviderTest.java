package com.example.maintainer.member.adapter.out.token;

import com.example.maintainer.member.domain.PhoneNumber;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtTokenProviderTest {

  @Test
  void 만료일이_지난토큰은_해독되지않는다() {
    JwtTokenProvider provider = new JwtTokenProvider();
    ReflectionTestUtils.setField(provider, "key",
        "-45-118-10532-3-4179-82516290-2988-864584-37-83-1201148-744188-12-120-1082818104181");
    final long REFRESH_TOKEN_VALIDITY_MILLISECONDS = 86400000 * 2;
    String expiredToken = provider.generateToken(new PhoneNumber("010-1234-1234"),
        new Date(new Date().getTime() - REFRESH_TOKEN_VALIDITY_MILLISECONDS));

    Assertions.assertThatCode(() -> provider.decryptedToken(expiredToken))
        .isExactlyInstanceOf(InvalidTokenException.class);
  }
}