package com.trendyol.ecommerce.shoppingcart.configuration;

import com.trendyol.ecommerce.shoppingcart.calculator.ShoppingCartCalculator;
import com.trendyol.ecommerce.shoppingcart.calculator.ShoppingCartItemCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculatorConfiguration {

    @Bean
    public ShoppingCartCalculator shoppingCartCalculator(ShoppingCartItemCalculator shoppingCartItemCalculator) {
        return new ShoppingCartCalculator(shoppingCartItemCalculator);
    }

    @Bean
    public ShoppingCartItemCalculator shoppingCartItemCalculator() {
        return new ShoppingCartItemCalculator();
    }
}
