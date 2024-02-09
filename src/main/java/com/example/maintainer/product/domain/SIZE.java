package com.example.maintainer.product.domain;

import java.util.Arrays;

public enum SIZE {
  SMALL, LARGE;

  public static SIZE getMatchSize(String sizeCandidate) {
    return Arrays.stream(SIZE.values())
        .filter(size -> size.name().toLowerCase().equals(sizeCandidate))
        .findAny()
        .orElseThrow(IllegalSizeException::new);
  }
}
