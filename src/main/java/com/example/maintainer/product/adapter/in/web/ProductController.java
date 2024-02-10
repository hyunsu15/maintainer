package com.example.maintainer.product.adapter.in.web;

import com.example.maintainer.product.application.port.in.ProductUseCase;
import com.example.maintainer.product.domain.CursorId;
import com.example.maintainer.product.domain.Product;
import com.example.maintainer.product.domain.ProductSearch;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

  private final ProductUseCase useCase;

  @PostMapping("")
  ResponseEntity<CustomResponse> create(PhoneNumber phoneNumber,
      @RequestBody ProductCreateRequest request) {
    useCase.create(phoneNumber.phoneNumber(),
        Product.create()
            .name(request.name())
            .size(request.size())
            .barcode(request.barcode())
            .category(request.category())
            .cost(request.cost())
            .salePrice(request.salePrice())
            .description(request.description())
            .expiredDate(request.expiredDate())
            .build()
    );
    return ResponseEntity.ok()
        .body(new CustomResponse(new Meta(
            HttpStatus.OK.value(), "OK"), null));
  }

  @PatchMapping("{id}")
  ResponseEntity<CustomResponse> update(PhoneNumber phoneNumber,
      @PathVariable("id") Long id, @RequestBody ProductUpdateRequest request
  ) {
    useCase.update(phoneNumber.phoneNumber(),
        Product.create()
            .name(request.name())
            .size(request.size())
            .barcode(request.barcode())
            .category(request.category())
            .cost(request.cost())
            .salePrice(request.salePrice())
            .description(request.description())
            .expiredDate(request.expiredDate())
            .build(), id
    );
    return ResponseEntity.ok()
        .body(new CustomResponse(new Meta(
            HttpStatus.OK.value(), "OK"), null));
  }

  @DeleteMapping("{id}")
  ResponseEntity<CustomResponse> delete(PhoneNumber phoneNumber,
      @PathVariable("id") Long id) {
    useCase.delete(phoneNumber.phoneNumber(), id);
    return ResponseEntity.ok()
        .body(new CustomResponse(new Meta(
            HttpStatus.OK.value(), "OK"), null));
  }

  @GetMapping("{id}")
  ResponseEntity<CustomResponse<Product>> findProduct(PhoneNumber phoneNumber,
      @PathVariable("id") Long id) {
    Product product = useCase.findProduct(phoneNumber.phoneNumber(), id);
    if (product == null) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok()
        .body(new CustomResponse(new Meta(
            HttpStatus.OK.value(), "OK"), product));
  }

  @GetMapping("search")
  ResponseEntity<CustomResponse<List<ProductSearch>>> findProductBySearch(PhoneNumber phoneNumber,
      ProductSearchRequest request) {
    List<ProductSearch> products = useCase.findProductsBySearch(phoneNumber.phoneNumber(),
        request.searchValue());
    if (products.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok()
        .body(new CustomResponse(new Meta(
            HttpStatus.OK.value(), "OK"), products));
  }

  @GetMapping("list")
  ResponseEntity<CustomResponse<List<ProductSearchWithNextCursorIdResponse>>> findProductsByCursorId(
      PhoneNumber phoneNumber,
      ProductFindByCursorIdRequest request) {
    List<ProductSearch> products = useCase.findProductsByCursorId(phoneNumber.phoneNumber(),
        new CursorId(request.cursorId()));
    if (products.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    CursorId nextCursorId = useCase.findNextCursorId(phoneNumber.phoneNumber(),
        new CursorId(products.getLast().id()));
    return ResponseEntity.ok()
        .body(new CustomResponse(new Meta(
            HttpStatus.OK.value(), "OK"),
            new ProductSearchWithNextCursorIdResponse(products, nextCursorId.getId())));
  }

}
