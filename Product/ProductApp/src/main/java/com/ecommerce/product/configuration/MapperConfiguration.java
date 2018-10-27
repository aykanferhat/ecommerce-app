package com.ecommerce.product.configuration;

import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.mapper.CategoryMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapper();
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapper();
    }
}
