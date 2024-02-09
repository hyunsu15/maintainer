package com.example.maintainer.product.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long>,
    JpaSpecificationExecutor<ProductJpaEntity> {

  boolean existsByMemberPhoneNumberAndId(String memberPhoneNumber, Long id);
}
