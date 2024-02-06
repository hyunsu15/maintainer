package com.example.maintainer.config.exception;

import lombok.Getter;

@Getter
class ErrorResponse {

  private final ErrorMeta meta;
  private final Object data;

  public ErrorResponse(ErrorMeta errorMeta) {
    this.meta = errorMeta;
    data = null;
  }
}
