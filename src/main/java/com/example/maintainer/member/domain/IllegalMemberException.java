package com.example.maintainer.member.domain;

import com.example.maintainer.config.exception.BaseException;

class IllegalMemberException extends BaseException {

  private static final String EXCEPTION_MESSAGE = "없는 회원입니다. 오타가 있을 수 있으니, 다시 입력해보세요.";

  public IllegalMemberException() {
    super(EXCEPTION_MESSAGE);
  }
}
