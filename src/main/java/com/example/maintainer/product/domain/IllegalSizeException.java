package com.example.maintainer.product.domain;

import com.example.maintainer.config.exception.BaseException;

class IllegalSizeException extends BaseException {

  private static final String EXCEPTION_MESSAGE = "잘못된 사이즈입니다.";

  public IllegalSizeException() {
    super(EXCEPTION_MESSAGE);
  }
}