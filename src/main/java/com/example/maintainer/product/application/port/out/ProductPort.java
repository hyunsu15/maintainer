package com.example.maintainer.product.application.port.out;

import com.example.maintainer.product.domain.CursorId;
import com.example.maintainer.product.domain.Product;
import com.example.maintainer.product.domain.ProductSearch;
import java.util.List;

public interface ProductPort {

  void save(String phoneNumber, Product product);

  boolean existProduct(String phoneNumber, Long productId);

  void update(Product product, Long productId);

  void delete(String phoneNumber, Long productId);

  Product findProduct(String phoneNumber, Long productId);

  List<ProductSearch> findProductByLike(String phoneNumber, String searchValue);

  List<ProductSearch> findProductByFirstWord(String phoneNumber, String searchValue);

  List<ProductSearch> findProductsByCursorId(String phoneNumber, CursorId cursorId);

  CursorId findNextCursorId(String phoneNumber, CursorId cursorId);
}
