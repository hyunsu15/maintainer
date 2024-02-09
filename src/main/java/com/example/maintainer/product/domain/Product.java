package com.example.maintainer.product.domain;

import java.time.LocalDateTime;
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
  private LocalDateTime expiredDate;

  public Product() {
  }

  @Builder(builderMethodName = "create", builderClassName = "string")
  public Product(String category, Long salePrice, Long cost, String name, String description,
      Long barcode, String size, LocalDateTime expiredDate) {
    this.category = category;
    this.salePrice = salePrice;
    this.cost = cost;
    this.name = name;
    this.description = description;
    this.barcode = barcode;
    this.size = SIZE.getMatchSize(size);
    this.expiredDate = expiredDate;
  }

  @Builder(builderMethodName = "detail", builderClassName = "size")
  public Product(String category, Long salePrice, Long cost, String name, String description,
      Long barcode, SIZE size, LocalDateTime expiredDate) {
    this.category = category;
    this.salePrice = salePrice;
    this.cost = cost;
    this.name = name;
    this.description = description;
    this.barcode = barcode;
    this.size = size;
    this.expiredDate = expiredDate;
  }

  public void validate(boolean existProduct) {
    if (!existProduct) {
      throw new ProductNotFoundException();
    }
  }
}
