package com.example.maintainer.product.application.port.in;

import com.example.maintainer.product.domain.CursorId;
import com.example.maintainer.product.domain.Product;
import com.example.maintainer.product.domain.ProductSearch;
import java.util.List;

public interface ProductUseCase {

  void create(String phoneNumber, Product product);

  void update(String phoneNumber, Product product, Long productId);

  void delete(String phoneNumber, Long productId);

  Product findProduct(String phoneNumber, Long productId);

  List<ProductSearch> findProductsBySearch(String phoneNumber, String searchValue);

  List<ProductSearch> findProductsByCursorId(String phoneNumber, CursorId cursorId);

  CursorId findNextCursorId(String phoneNumber, CursorId lastCursorId);
}
