package com.ecommerce.basket.configuration;

import com.ecommerce.basket.calculator.BasketCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculatorConfiguration {

    @Bean
    public BasketCalculator basketCalculator() {
        return new BasketCalculator();
    }
}

