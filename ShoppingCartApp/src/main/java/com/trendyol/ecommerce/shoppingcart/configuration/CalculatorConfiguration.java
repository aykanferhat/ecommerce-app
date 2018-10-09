package com.trendyol.ecommerce.shoppingcart.configuration;

import com.trendyol.ecommerce.shoppingcart.calculator.ShoppingCartCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculatorConfiguration {

    @Bean
    public ShoppingCartCalculator shoppingCartCalculator() {
        return new ShoppingCartCalculator();
    }
}
