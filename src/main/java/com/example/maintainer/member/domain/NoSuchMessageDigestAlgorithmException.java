package com.example.maintainer.member.domain;

public class NoSuchMessageDigestAlgorithmException extends RuntimeException {

  private static final String EXCEPTION_MESSAGE = "올바르지 않은 암호 알고리즘입니다.";

  public NoSuchMessageDigestAlgorithmException() {
    super(EXCEPTION_MESSAGE);
  }
}
