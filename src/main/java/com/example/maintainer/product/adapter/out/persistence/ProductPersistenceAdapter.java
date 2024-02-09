package com.example.maintainer.product.adapter.out.persistence;

import com.example.maintainer.product.application.port.out.ProductPort;
import com.example.maintainer.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPort {

  private final ProductJpaRepository productJpaRepository;

  @Override
  public void save(String phoneNumber, Product product) {
    productJpaRepository.save(new ProductJpaEntity(phoneNumber, product));
  }
}
