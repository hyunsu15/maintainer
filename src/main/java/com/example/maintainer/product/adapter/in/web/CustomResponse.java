package com.example.maintainer.product.adapter.in.web;

import lombok.Getter;

@Getter
class CustomResponse<T> {

  private final Meta meta;
  private final T data;

  public CustomResponse(Meta meta, T data) {
    this.meta = meta;
    this.data = data;
  }
}
