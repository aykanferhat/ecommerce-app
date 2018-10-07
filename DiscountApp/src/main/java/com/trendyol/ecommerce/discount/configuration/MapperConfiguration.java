package com.trendyol.ecommerce.discount.configuration;

import com.trendyol.ecommerce.discount.mapper.CampaignMapper;
import com.trendyol.ecommerce.discount.mapper.CouponMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public CouponMapper couponMapper() {
        return new CouponMapper();
    }

    @Bean
    public CampaignMapper campaignMapper() {
        return new CampaignMapper();
    }
}
