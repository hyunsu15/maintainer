package com.example.maintainer.product.adapter.out.persistence;

import com.example.maintainer.product.application.port.out.ProductPort;
import com.example.maintainer.product.domain.FirstInitial;
import com.example.maintainer.product.domain.Product;
import com.example.maintainer.product.domain.ProductSearch;
import com.example.maintainer.product.util.CustomReflectionUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPort {

  private final ProductJpaRepository productJpaRepository;
  private final ProductSearchJpaRepository productSearchJpaRepository;

  @Override
  public void save(String phoneNumber, Product product) {
    ProductJpaEntity productJpaEntity = productJpaRepository.save(
        new ProductJpaEntity(phoneNumber, product));
    productSearchJpaRepository.save(
        new ProductSearchJpaEntity(productJpaEntity.getId(),
            new FirstInitial(product.getName()).getValue()));
  }

  @Override
  public boolean existProduct(String phoneNumber, Long productId) {
    return productJpaRepository.existsByMemberPhoneNumberAndId(phoneNumber, productId);
  }

  @Override
  public void update(Product product, Long productId) {
    ProductJpaEntity productJpaEntity = productJpaRepository.findById(productId).get();
    CustomReflectionUtil.setFieldValues(productJpaEntity, product);
    ProductSearchJpaEntity productSearchJpaEntity = productSearchJpaRepository.findByProductId(
        productJpaEntity.getId()).get();
    productSearchJpaEntity.updateFirstInitial(new FirstInitial(productJpaEntity.getName()));
    productJpaRepository.save(productJpaEntity);
  }

  @Override
  public void delete(String phoneNumber, Long productId) {
    productJpaRepository.deleteById(productId);
    productSearchJpaRepository.deleteByProductId(productId);
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
        .expiredDate(productJpaEntity.getExpiredDate())
        .build();
  }


  @Override
  public List<ProductSearch> getProductByLike(String phoneNumber, String searchValue) {
    return productJpaRepository.findAll(equalsPhoneNumber(phoneNumber)
            .and(likeName(searchValue)))
        .stream()
        .map(productSearchVo -> new ProductSearch(productSearchVo.getId(),
            productSearchVo.getSalePrice(),
            productSearchVo.getCost(), productSearchVo.getName(), productSearchVo.getSize(),
            productSearchVo.getExpiredDate()))
        .toList();
  }

  @Override
  public List<ProductSearch> getProductByFirstWord(String phoneNumber, String searchValue) {
    List<Long> productIds = productSearchJpaRepository.findAll(
            (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstInitialName"),
                "%" + searchValue + "%"))
        .stream()
        .map(ProductSearchJpaEntity::getProductId)
        .toList();

    return productJpaRepository.findAll(equalsPhoneNumber(phoneNumber).and(
            findByIds(productIds)))
        .stream()
        .map(productSearchVo -> new ProductSearch(productSearchVo.getId(),
            productSearchVo.getSalePrice(),
            productSearchVo.getCost(), productSearchVo.getName(), productSearchVo.getSize(),
            productSearchVo.getExpiredDate()))
        .toList();
  }

  private Specification<ProductJpaEntity> equalsPhoneNumber(String phoneNumber) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("memberPhoneNumber"),
        phoneNumber);
  }

  private Specification<ProductJpaEntity> likeName(String searchValue) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
        "%" + searchValue + "%");
  }

  private Specification<ProductJpaEntity> findByIds(List<Long> ids) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.and(root.get("id").in(ids));
  }
}
