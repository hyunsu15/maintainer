package com.example.maintainer.member.adapter.out.token;

import com.example.maintainer.member.application.port.out.TokenProvider;
import com.example.maintainer.member.domain.PhoneNumber;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

  private static final long REFRESH_TOKEN_VALIDITY_MILLISECONDS = 86400000;

  @Override
  public String generateToken(PhoneNumber phoneNumber, Date now) {
    Date expiredDate = new Date(now.getTime() + REFRESH_TOKEN_VALIDITY_MILLISECONDS);
    MacAlgorithm alg = Jwts.SIG.HS512; //or HS384 or HS256
    SecretKey key = alg.key().build();
    return Jwts.builder()
        .subject(phoneNumber.getPhoneNumber())
        .issuedAt(now)
        .expiration(expiredDate)
        .signWith(key, alg)
        .compact();
  }
}
