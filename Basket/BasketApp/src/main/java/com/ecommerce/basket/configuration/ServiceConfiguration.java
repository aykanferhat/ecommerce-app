package com.ecommerce.basket.configuration;

import com.ecommerce.basket.calculator.BasketCalculator;
import com.ecommerce.basket.mapper.BasketItemMapper;
import com.ecommerce.basket.mapper.BasketMapper;
import com.ecommerce.basket.repository.BasketItemRepository;
import com.ecommerce.basket.repository.BasketRepository;
import com.ecommerce.basket.service.BasketItemService;
import com.ecommerce.basket.service.BasketItemServiceImpl;
import com.ecommerce.basket.service.BasketService;
import com.ecommerce.basket.service.BasketServiceImpl;
import com.ecommerce.discount.client.CampaignServiceClient;
import com.ecommerce.discount.client.CouponServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public BasketService basketService(BasketRepository basketRepository,
                                       BasketMapper basketMapper,
                                       BasketItemService basketItemService,
                                       BasketCalculator basketCalculator,
                                       CouponServiceClient couponServiceClient) {
        return BasketServiceImpl.builder()
                .basketRepository(basketRepository)
                .basketMapper(basketMapper)
                .basketItemService(basketItemService)
                .basketCalculator(basketCalculator)
                .couponServiceClient(couponServiceClient)
                .build();
    }

    @Bean
    public BasketItemService basketItemService(BasketRepository basketRepository,
                                               BasketItemRepository basketItemRepository,
                                               BasketItemMapper basketItemMapper,
                                               CampaignServiceClient campaignServiceClient) {
        return BasketItemServiceImpl.builder()
                .basketItemMapper(basketItemMapper)
                .basketItemRepository(basketItemRepository)
                .basketRepository(basketRepository)
                .campaignServiceClient(campaignServiceClient)
                .build();
    }
}


