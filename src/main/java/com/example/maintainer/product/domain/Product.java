package com.example.maintainer.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {

  private String category;
  private Long salePrice;
  private Long cost;
  private String name;
  private String description;
  private Long barcode;
  private SIZE size;

  public Product() {
  }

  @Builder(builderMethodName = "create", builderClassName = "string")
  public Product(String category, Long salePrice, Long cost, String name, String description,
      Long barcode, String size) {
    this.category = category;
    this.salePrice = salePrice;
    this.cost = cost;
    this.name = name;
    this.description = description;
    this.barcode = barcode;
    this.size = SIZE.getMatchSize(size);
  }

  @Builder(builderMethodName = "detail", builderClassName = "size")
  public Product(String category, Long salePrice, Long cost, String name, String description,
      Long barcode, SIZE size) {
    this.category = category;
    this.salePrice = salePrice;
    this.cost = cost;
    this.name = name;
    this.description = description;
    this.barcode = barcode;
    this.size = size;
  }

  public void validate(boolean existProduct) {
    if (!existProduct) {
      throw new ProductNotFoundException();
    }
  }
}
