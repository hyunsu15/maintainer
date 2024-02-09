package com.example.maintainer.product.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

record ProductCreateRequest(
    String category,
    Long salePrice,
    Long cost,
    String name,
    String description,
    Long barcode,
    String size,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime expiredDate) {

}
