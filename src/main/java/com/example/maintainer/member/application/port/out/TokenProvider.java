package com.example.maintainer.member.application.port.out;

import com.example.maintainer.member.domain.PhoneNumber;
import java.util.Date;

public interface TokenProvider {

  String generateToken(PhoneNumber phoneNumber, Date now);

  String decryptedToken(String token);
}
