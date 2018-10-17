package com.ecommerce.basket.configuration;

import com.ecommerce.basket.mapper.BasketItemMapper;
import com.ecommerce.basket.mapper.BasketMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public BasketItemMapper basketItemMapper() {
        return new BasketItemMapper();
    }

    @Bean
    public BasketMapper basketMapper() {
        return new BasketMapper();
    }
}
