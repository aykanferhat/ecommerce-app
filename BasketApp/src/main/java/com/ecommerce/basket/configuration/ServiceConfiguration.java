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
import com.ecommerce.core.util.CampaignDiscountCalculator;
import com.ecommerce.core.util.CouponDiscountCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfiguration {

    @Bean
    public BasketService basketService(BasketRepository basketRepository,
                                       BasketMapper basketMapper,
                                       BasketItemService basketItemService,
                                       CouponDiscountCalculator couponDiscountCalculator,
                                       BasketCalculator basketCalculator,
                                       RestTemplate restTemplate) {
        return BasketServiceImpl.builder()
                .basketRepository(basketRepository)
                .basketMapper(basketMapper)
                .basketItemService(basketItemService)
                .basketCalculator(basketCalculator)
                .couponDiscountCalculator(couponDiscountCalculator)
                .restTemplate(restTemplate)
                .build();
    }

    @Bean
    public BasketItemService basketItemService(BasketRepository basketRepository,
                                               BasketItemRepository basketItemRepository,
                                               BasketItemMapper basketItemMapper,
                                               CampaignDiscountCalculator campaignDiscountCalculator,
                                               RestTemplate restTemplate) {
        return BasketItemServiceImpl.builder()
                .basketItemMapper(basketItemMapper)
                .campaignDiscountCalculator(campaignDiscountCalculator)
                .restTemplate(restTemplate)
                .basketItemRepository(basketItemRepository)
                .basketRepository(basketRepository)
                .build();
    }
}


