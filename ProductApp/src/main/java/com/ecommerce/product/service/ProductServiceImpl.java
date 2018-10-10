package com.ecommerce.product.service;

import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.model.request.ProductCreateRequest;
import com.ecommerce.product.model.response.ProductCreateResponse;
import com.ecommerce.core.domain.Product;
import com.ecommerce.core.entity.ProductEntity;
import com.ecommerce.core.exception.BusinessException;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.ecommerce.core.exception.faultCode.CategoryFaultCode.CATEGORY_NOT_FOUND;
import static com.ecommerce.core.exception.faultCode.ProductFaultCode.PRODUCT_NOT_FOUND;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @Override
    public ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest) {
        Long categoryId = productCreateRequest.getCategoryId();
        if (!categoryService.existsById(categoryId)) {
            throw new BusinessException(CATEGORY_NOT_FOUND, categoryId);
        }
        ProductEntity productEntity = productRepository.save(productMapper.mapToEntity(productCreateRequest));
        return productMapper.mapToResponse(productEntity);
    }

    @Override
    public Product getProductById(Long productId) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
        if (!optionalProductEntity.isPresent()) {
            throw new BusinessException(PRODUCT_NOT_FOUND, productId);
        }
        ProductEntity productEntity = optionalProductEntity.get();
        return productMapper.mapToDTO(productEntity);
    }

    @Override
    public boolean existsById(Long productId) {
        return productRepository.existsById(productId);
    }
}
