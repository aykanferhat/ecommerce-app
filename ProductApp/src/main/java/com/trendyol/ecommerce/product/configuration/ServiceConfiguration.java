package com.trendyol.ecommerce.product.configuration;

import com.trendyol.ecommerce.product.mapper.CategoryMapper;
import com.trendyol.ecommerce.product.mapper.ProductMapper;
import com.trendyol.ecommerce.product.repository.CategoryRepository;
import com.trendyol.ecommerce.product.repository.ProductRepository;
import com.trendyol.ecommerce.product.service.CategoryService;
import com.trendyol.ecommerce.product.service.CategoryServiceImpl;
import com.trendyol.ecommerce.product.service.ProductService;
import com.trendyol.ecommerce.product.service.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public ProductService productService(ProductRepository productRepository,
                                         CategoryService categoryService,
                                         ProductMapper productMapper) {
        return new ProductServiceImpl(productRepository, categoryService, productMapper);
    }

    @Bean
    public CategoryService categoryService(CategoryRepository categoryRepository,
                                           CategoryMapper categoryMapper) {
        return new CategoryServiceImpl(categoryRepository, categoryMapper);
    }
}
