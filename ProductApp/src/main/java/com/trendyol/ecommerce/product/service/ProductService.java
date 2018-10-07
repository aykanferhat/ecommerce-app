package com.trendyol.ecommerce.product.service;

import com.trendyol.ecommerce.core.domain.Product;
import com.trendyol.ecommerce.product.model.request.ProductCreateRequest;
import com.trendyol.ecommerce.product.model.response.ProductCreateResponse;

public interface ProductService {

    ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest);

    Product getProductById(Long productId);

    boolean existsById(Long productId);
}
