package com.example.maintainer.product.application.service;

import com.example.maintainer.product.application.port.in.ProductUseCase;
import com.example.maintainer.product.application.port.out.ProductPort;
import com.example.maintainer.product.domain.Product;
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
}
