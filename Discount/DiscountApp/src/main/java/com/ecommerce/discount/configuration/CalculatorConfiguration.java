package com.ecommerce.discount.configuration;

import com.ecommerce.discount.calculator.CampaignDiscountCalculator;
import com.ecommerce.discount.calculator.CampaignDiscountCalculatorImpl;
import com.ecommerce.discount.calculator.CouponDiscountCalculator;
import com.ecommerce.discount.calculator.CouponDiscountCalculatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculatorConfiguration {

    @Bean
    public CampaignDiscountCalculator campaignDiscountCalculator() {
        return new CampaignDiscountCalculatorImpl();
    }

    @Bean
    public CouponDiscountCalculator couponDiscountCalculator() {
        return new CouponDiscountCalculatorImpl();
    }

}
