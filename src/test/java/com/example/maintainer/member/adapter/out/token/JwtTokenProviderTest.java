package com.example.maintainer.member.adapter.out.token;

import com.example.maintainer.member.application.port.out.BlackListPort;
import com.example.maintainer.member.domain.PhoneNumber;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtTokenProviderTest {

  @Test
  void 만료일이_지난토큰은_해독되지않는다() {
    BlackListPort port = new FakeBlackListPort();

    JwtTokenProvider provider = new JwtTokenProvider(port);
    ReflectionTestUtils.setField(provider, "key",
        "-45-118-10532-3-4179-82516290-2988-864584-37-83-1201148-744188-12-120-1082818104181");
    final long REFRESH_TOKEN_VALIDITY_MILLISECONDS = 86400000 * 2;
    String expiredToken = provider.generateToken(new PhoneNumber("010-1234-1234"),
        new Date(new Date().getTime() - REFRESH_TOKEN_VALIDITY_MILLISECONDS));

    Assertions.assertThatCode(() -> provider.decryptedToken(expiredToken))
        .isExactlyInstanceOf(InvalidTokenException.class);
  }

  @Test
  void 블랙리스트에_들어간_토큰은_해독되지않는다() {
    BlackListPort port = new FakeBlackListPort();
    JwtTokenProvider provider = new JwtTokenProvider(port);
    ReflectionTestUtils.setField(provider, "key",
        "-45-118-10532-3-4179-82516290-2988-864584-37-83-1201148-744188-12-120-1082818104181");
    final long REFRESH_TOKEN_VALIDITY_MILLISECONDS = 86400000 - 6000;
    String token = provider.generateToken(new PhoneNumber("010-1234-1234"),
        new Date(new Date().getTime() - REFRESH_TOKEN_VALIDITY_MILLISECONDS));
    port.save(token);

    Assertions.assertThatCode(() -> provider.decryptedToken(token))
        .isExactlyInstanceOf(InvalidTokenException.class);
  }

  class FakeBlackListPort implements BlackListPort {

    Map<String, String> map = new HashMap<>();

    @Override
    public void save(String token) {
      map.put(token, token);
    }

    @Override
    public boolean isExist(String token) {
      return map.get(token) != null;
    }
  }
}
