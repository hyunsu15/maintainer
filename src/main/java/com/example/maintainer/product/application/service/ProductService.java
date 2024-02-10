package com.example.maintainer.product.application.service;

import com.example.maintainer.product.application.port.in.ProductUseCase;
import com.example.maintainer.product.application.port.out.ProductPort;
import com.example.maintainer.product.domain.CursorId;
import com.example.maintainer.product.domain.Product;
import com.example.maintainer.product.domain.ProductSearch;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

  private final ProductPort productPort;

  @Override
  public void create(String phoneNumber, Product product) {
    productPort.save(phoneNumber, product);
  }

  @Override
  public void update(String phoneNumber, Product product, Long productId) {
    boolean existProduct = productPort.existProduct(phoneNumber, productId);
    product.validateElseException(existProduct);
    productPort.update(product, productId);
  }

  @Override
  public void delete(String phoneNumber, Long productId) {
    boolean existProduct = productPort.existProduct(phoneNumber, productId);
    new Product().validateElseException(existProduct);
    productPort.delete(phoneNumber, productId);
  }

  @Override
  public Product getProduct(String phoneNumber, Long productId) {
    boolean existProduct = productPort.existProduct(phoneNumber, productId);
    return new Product().getProduct(existProduct,
        () -> productPort.getProduct(phoneNumber, productId));
  }

  @Override
  public List<ProductSearch> getProductBySearch(String phoneNumber, String searchValue) {
    return Stream.concat(
            productPort.getProductByLike(phoneNumber, searchValue).stream(),
            productPort.getProductByFirstWord(phoneNumber, searchValue).stream()
        )
        .distinct()
        .toList();
  }

  @Override
  public List<ProductSearch> findProductsByCursorId(String phoneNumber, CursorId cursorId) {
    return productPort.findProductsByCursorId(phoneNumber, cursorId);
  }

  @Override
  public CursorId findNextCursorId(String phoneNumber, CursorId cursorId) {
    return productPort.findNextCursorId(phoneNumber, cursorId);
  }
}
