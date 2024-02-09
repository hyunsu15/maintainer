package com.example.maintainer.product.application.port.out;

import com.example.maintainer.product.domain.Product;

public interface ProductPort {

  void save(String phoneNumber, Product product);

  boolean existProduct(String phoneNumber, Long productId);

  void update(Product product, Long productId);

  void delete(String phoneNumber, Long productId);

  Product getProduct(String phoneNumber, Long productId);
}
