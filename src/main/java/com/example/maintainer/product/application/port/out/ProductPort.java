package com.example.maintainer.product.application.port.out;

import com.example.maintainer.product.domain.Product;

public interface ProductPort {

  void save(String phoneNumber, Product product);
}
