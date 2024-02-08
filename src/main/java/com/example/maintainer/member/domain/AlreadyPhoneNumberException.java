package com.example.maintainer.member.domain;

import com.example.maintainer.config.exception.BaseException;

class AlreadyPhoneNumberException extends BaseException {

  private static final String EXCEPTION_MESSAGE = "이미 가입된 휴대폰 번호입니다.";

  public AlreadyPhoneNumberException() {
    super(EXCEPTION_MESSAGE);
  }
}
