package com.trendyol.ecommerce.product.configuration;

import com.trendyol.ecommerce.product.mapper.CategoryMapper;
import com.trendyol.ecommerce.product.mapper.ProductMapper;
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
