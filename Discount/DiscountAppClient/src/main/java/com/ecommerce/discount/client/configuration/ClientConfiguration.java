package com.ecommerce.discount.client.configuration;

import com.ecommerce.discount.client.CampaignServiceClient;
import com.ecommerce.discount.client.CouponServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {

    @Bean
    public CampaignServiceClient campaignServiceClient(RestTemplate restTemplate) {
        return new CampaignServiceClient(restTemplate);
    }

    @Bean
    public CouponServiceClient couponServiceClient(RestTemplate restTemplate) {
        return new CouponServiceClient(restTemplate);
    }
}
