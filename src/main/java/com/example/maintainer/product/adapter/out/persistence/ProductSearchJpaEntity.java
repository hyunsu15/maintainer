package com.example.maintainer.product.adapter.out.persistence;

import com.example.maintainer.product.domain.FirstInitial;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "product_first_initial_name")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class ProductSearchJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long productId;
  private String firstInitialName;

  public ProductSearchJpaEntity(Long productId, String firstInitialName) {
    this.productId = productId;
    this.firstInitialName = firstInitialName;
  }

  public void updateFirstInitial(FirstInitial firstInitial) {
    this.firstInitialName = firstInitial.getValue();
  }
}
