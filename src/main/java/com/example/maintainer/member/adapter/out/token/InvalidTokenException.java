package com.example.maintainer.member.adapter.out.token;

import com.example.maintainer.config.exception.BaseException;

public class InvalidTokenException extends BaseException {

  private static final String EXCEPTION_MESSAGE = "해독할수 없는 토큰입니다.";

  public InvalidTokenException() {
    super(EXCEPTION_MESSAGE);
  }
}

