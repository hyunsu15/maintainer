package com.example.maintainer.product.application.service;

import com.example.maintainer.member.application.port.out.TokenProvider;
import com.example.maintainer.product.application.port.in.SignInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService implements SignInPort {

  private final TokenProvider tokenProvider;

  @Override
  public String validateToken(String token) {
    return tokenProvider.decryptedToken(token);
  }
}
