package com.example.maintainer.product.adapter.out.persistence;

import com.example.maintainer.product.domain.Product;
import com.example.maintainer.product.domain.SIZE;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class ProductJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String memberPhoneNumber;
  @Column(nullable = false)
  private String category;
  @Column(nullable = false)
  private Long salePrice;
  @Column(nullable = false)
  private Long cost;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private Long barcode;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private SIZE size;

  ProductJpaEntity(String category, String memberPhoneNumber, Long salePrice, Long cost,
      String name,
      String description,
      Long barcode, SIZE size) {
    this.category = category;
    this.memberPhoneNumber = memberPhoneNumber;
    this.salePrice = salePrice;
    this.cost = cost;
    this.name = name;
    this.description = description;
    this.barcode = barcode;
    this.size = size;
  }

  ProductJpaEntity(String memberPhoneNumber, Product product) {
    this(product.getCategory(), memberPhoneNumber, product.getSalePrice(), product.getCost(),
        product.getName(), product.getDescription(), product.getBarcode(), product.getSize());
  }
}
