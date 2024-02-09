package com.example.maintainer.product.domain;

import com.example.maintainer.config.exception.BaseException;

public class ProductNotFoundException extends BaseException {

  private static final String EXCEPTION_MESSAGE = "없는 상품입니다.";

  public ProductNotFoundException() {
    super(EXCEPTION_MESSAGE);
  }
}