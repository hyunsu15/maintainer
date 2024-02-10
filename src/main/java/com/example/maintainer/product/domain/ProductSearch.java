package com.example.maintainer.product.domain;

import java.time.LocalDateTime;

public record ProductSearch(
    Long id,
    Long salePrice,
    Long cost,
    String name,
    SIZE size,
    LocalDateTime expiredDate
) {

}
