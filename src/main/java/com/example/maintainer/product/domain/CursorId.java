package com.example.maintainer.product.domain;

import lombok.Getter;

@Getter
public class CursorId {

  private Long id;

  public CursorId(Long id) {
    this.id = getIdElseDefault(id);
  }

  private Long getIdElseDefault(Long id) {
    if (id == null || id < 0) {
      return 0L;
    }
    return id;
  }

  public CursorId getNextCursorId() {
    return new CursorId(id + 1);
  }
}
