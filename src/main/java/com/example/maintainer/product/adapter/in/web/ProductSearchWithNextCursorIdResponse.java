package com.example.maintainer.product.adapter.in.web;

import com.example.maintainer.product.domain.ProductSearch;
import java.util.List;

record ProductSearchWithNextCursorIdResponse(
    List<ProductSearch> productSearches,
    Long nextCursorId
) {

}
