package com.trendyol.ecommerce.core.configuration;

import com.trendyol.ecommerce.core.util.*;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

    @Bean
    public CampaignDiscountCalculator campaignDiscountCalculator() {
        return new CampaignDiscountCalculatorImpl();
    }

    @Bean
    public CouponDiscountCalculator couponDiscountCalculator() {
        return new CouponDiscountCalculatorImpl();
    }

    @Bean
    public ExceptionMessageMapper exceptionMessageMapper(MessageSource messageSource) {
        return new ExceptionMessageMapper(messageSource);
    }
}
