package com.example.maintainer.product.adapter.out.persistence;

import com.example.maintainer.product.application.port.out.ProductPort;
import com.example.maintainer.product.domain.Product;
import com.example.maintainer.product.util.CustomReflectionUtil;
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

  @Override
  public boolean existProduct(String phoneNumber, Long productId) {
    return productJpaRepository.existsByMemberPhoneNumberAndId(phoneNumber, productId);
  }

  @Override
  public void update(Product product, Long productId) {
    ProductJpaEntity productJpaEntity = productJpaRepository.findById(productId).get();
    CustomReflectionUtil.setFieldValues(productJpaEntity, product);
    productJpaRepository.save(productJpaEntity);
  }

  @Override
  public void delete(String phoneNumber, Long productId) {
    productJpaRepository.deleteById(productId);
  }

  @Override
  public Product getProduct(String phoneNumber, Long productId) {
    ProductJpaEntity productJpaEntity = productJpaRepository.findById(productId).get();
    return Product.detail()
        .name(productJpaEntity.getName())
        .size(productJpaEntity.getSize())
        .cost(productJpaEntity.getCost())
        .salePrice(productJpaEntity.getSalePrice())
        .barcode(productJpaEntity.getBarcode())
        .description(productJpaEntity.getDescription())
        .category(productJpaEntity.getCategory())
        .build();
  }
}
