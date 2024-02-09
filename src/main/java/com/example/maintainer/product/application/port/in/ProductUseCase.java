package com.example.maintainer.product.application.port.in;

import com.example.maintainer.product.domain.Product;

public interface ProductUseCase {

  void create(String phoneNumber, Product product);

  void update(String phoneNumber, Product product, Long productId);

  void delete(String phoneNumber, Long productId);

  Product getProduct(String phoneNumber, Long productId);
}
