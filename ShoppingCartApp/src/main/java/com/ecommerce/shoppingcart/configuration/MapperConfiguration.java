package com.ecommerce.shoppingcart.configuration;

import com.ecommerce.shoppingcart.mapper.ShoppingCartItemMapper;
import com.ecommerce.shoppingcart.mapper.ShoppingCartMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public ShoppingCartItemMapper shoppingCartItemMapper() {
        return new ShoppingCartItemMapper();
    }

    @Bean
    public ShoppingCartMapper shoppingCartMapper() {
        return new ShoppingCartMapper();
    }
}
