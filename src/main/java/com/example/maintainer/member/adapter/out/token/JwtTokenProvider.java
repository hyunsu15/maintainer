package com.example.maintainer.member.adapter.out.token;

import com.example.maintainer.member.application.port.out.TokenProvider;
import com.example.maintainer.member.domain.PhoneNumber;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

  private static final long REFRESH_TOKEN_VALIDITY_MILLISECONDS = 86400000;
  @Value("${jwt.key}")
  private String key;

  @Override
  public String generateToken(PhoneNumber phoneNumber, Date now) {
    Date expiredDate = new Date(now.getTime() + REFRESH_TOKEN_VALIDITY_MILLISECONDS);
    byte[] bytes = key.getBytes();
    SecretKey secretKey = Keys.hmacShaKeyFor(bytes);

    return Jwts.builder()
        .subject(phoneNumber.getPhoneNumber())
        .issuedAt(now)
        .expiration(expiredDate)
        .signWith(secretKey)
        .compact();
  }

  @Override
  public String decryptedToken(String token) {
    byte[] bytes = key.getBytes();
    SecretKey secretKey = Keys.hmacShaKeyFor(bytes);

    try {
      return Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token)
          .getPayload()
          .getSubject();
    } catch (JwtException e) {
      throw new InvalidTokenException();
    }
  }
}
