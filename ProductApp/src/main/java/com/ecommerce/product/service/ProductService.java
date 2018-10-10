package com.ecommerce.product.service;

import com.ecommerce.product.model.request.ProductCreateRequest;
import com.ecommerce.product.model.response.ProductCreateResponse;
import com.ecommerce.core.domain.Product;

public interface ProductService {

    ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest);

    Product getProductById(Long productId);

    boolean existsById(Long productId);
}
