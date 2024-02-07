package com.example.maintainer.member.domain;

import com.example.maintainer.config.exception.BaseException;

public class IllegalPhoneNumberException extends BaseException {

  private static final String EXCEPTION_MESSAGE = "올바르지 않은 휴대폰 번호입니다.";

  public IllegalPhoneNumberException() {
    super(EXCEPTION_MESSAGE);
  }
}
