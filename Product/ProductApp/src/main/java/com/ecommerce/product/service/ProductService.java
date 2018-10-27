package com.ecommerce.product.service;

import com.ecommerce.common.domain.Product;
import com.ecommerce.common.domain.ProductCreateRequest;
import com.ecommerce.common.domain.ProductCreateResponse;

public interface ProductService {

    ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest);

    Product getProductById(Long productId);

    boolean existsById(Long productId);
}
