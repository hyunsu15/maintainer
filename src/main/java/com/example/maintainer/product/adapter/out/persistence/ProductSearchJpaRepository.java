package com.example.maintainer.product.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductSearchJpaRepository extends JpaRepository<ProductSearchJpaEntity, Long>,
    JpaSpecificationExecutor<ProductSearchJpaEntity> {

  Optional<ProductSearchJpaEntity> findByProductId(Long productId);

  void deleteByProductId(Long productId);
}
