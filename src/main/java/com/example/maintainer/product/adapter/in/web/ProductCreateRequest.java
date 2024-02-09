package com.example.maintainer.product.adapter.in.web;

record ProductCreateRequest(
    String category,
    Long salePrice,
    Long cost,
    String name,
    String description,
    Long barcode,
    String size) {

}
