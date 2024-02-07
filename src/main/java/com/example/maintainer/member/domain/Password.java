package com.example.maintainer.member.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.Getter;

@Getter
public class Password {

  private static final String ENCRYPT_ALGORITHM_NAME = "SHA-256";
  private final String password;

  public Password(String password) {
    this.password = encryptPassword(password);
  }

  private String encryptPassword(String password) {
    try {
      MessageDigest digest = MessageDigest.getInstance(ENCRYPT_ALGORITHM_NAME);
      byte[] hash = digest.digest(password.getBytes());
      StringBuffer encryptPassword = new StringBuffer();
      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) {
          encryptPassword.append('0');
        }
        encryptPassword.append(hex);
      }
      return encryptPassword.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new NoSuchMessageDigestAlgorithmException();
    }
  }
}
