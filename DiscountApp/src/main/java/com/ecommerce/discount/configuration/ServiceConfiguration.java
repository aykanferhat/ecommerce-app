package com.ecommerce.discount.configuration;

import com.ecommerce.discount.mapper.CampaignMapper;
import com.ecommerce.discount.mapper.CouponMapper;
import com.ecommerce.discount.repository.CampaignRepository;
import com.ecommerce.discount.repository.CouponRepository;
import com.ecommerce.discount.service.CampaignService;
import com.ecommerce.discount.service.CampaignServiceImpl;
import com.ecommerce.discount.service.CouponService;
import com.ecommerce.discount.service.CouponServiceImpl;
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
