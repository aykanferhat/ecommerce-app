package com.trendyol.ecommerce.discount.configuration;

import com.trendyol.ecommerce.discount.mapper.CampaignMapper;
import com.trendyol.ecommerce.discount.mapper.CouponMapper;
import com.trendyol.ecommerce.discount.repository.CampaignRepository;
import com.trendyol.ecommerce.discount.repository.CouponRepository;
import com.trendyol.ecommerce.discount.service.CampaignService;
import com.trendyol.ecommerce.discount.service.CampaignServiceImpl;
import com.trendyol.ecommerce.discount.service.CouponService;
import com.trendyol.ecommerce.discount.service.CouponServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfiguration {

    @Bean
    public CampaignService campaignService(CampaignRepository campaignRepository,
                                           CampaignMapper campaignMapper,
                                           RestTemplate restTemplate) {
        return new CampaignServiceImpl(campaignRepository, campaignMapper, restTemplate);
    }

    @Bean
    public CouponService couponService(CouponRepository couponRepository,
                                       CouponMapper couponMapper) {
        return new CouponServiceImpl(couponRepository, couponMapper);
    }

}
