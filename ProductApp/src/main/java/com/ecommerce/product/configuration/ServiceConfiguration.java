package com.ecommerce.product.configuration;

import com.ecommerce.product.mapper.CategoryMapper;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.CategoryService;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.service.ProductServiceImpl;
import com.ecommerce.product.service.CategoryServiceImpl;
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
