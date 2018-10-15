package com.ecommerce.shoppingcart.configuration;

import com.ecommerce.shoppingcart.calculator.ShoppingCartCalculator;
import com.ecommerce.shoppingcart.mapper.ShoppingCartItemMapper;
import com.ecommerce.shoppingcart.mapper.ShoppingCartMapper;
import com.ecommerce.shoppingcart.repository.ShoppingCartItemRepository;
import com.ecommerce.shoppingcart.repository.ShoppingCartRepository;
import com.ecommerce.shoppingcart.service.ShoppingCartItemService;
import com.ecommerce.shoppingcart.service.ShoppingCartItemServiceImpl;
import com.ecommerce.shoppingcart.service.ShoppingCartService;
import com.ecommerce.shoppingcart.service.ShoppingCartServiceImpl;
import com.ecommerce.core.util.CampaignDiscountCalculator;
import com.ecommerce.core.util.CouponDiscountCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfiguration {

    @Bean
    public ShoppingCartService shoppingCartService(ShoppingCartRepository shoppingCartRepository,
                                                   ShoppingCartMapper shoppingCartMapper,
                                                   ShoppingCartItemService shoppingCartItemService,
                                                   CouponDiscountCalculator couponDiscountCalculator,
                                                   ShoppingCartCalculator shoppingCartCalculator,
                                                   RestTemplate restTemplate) {
        return ShoppingCartServiceImpl.builder()
                .shoppingCartRepository(shoppingCartRepository)
                .shoppingCartMapper(shoppingCartMapper)
                .shoppingCartItemService(shoppingCartItemService)
                .shoppingCartCalculator(shoppingCartCalculator)
                .couponDiscountCalculator(couponDiscountCalculator)
                .restTemplate(restTemplate)
                .build();
    }

    @Bean
    public ShoppingCartItemService shoppingCartItemService(ShoppingCartRepository shoppingCartRepository,
                                                           ShoppingCartItemRepository shoppingCartItemRepository,
                                                           ShoppingCartItemMapper shoppingCartItemMapper,
                                                           CampaignDiscountCalculator campaignDiscountCalculator,
                                                           RestTemplate restTemplate) {
        return ShoppingCartItemServiceImpl.builder()
                .shoppingCartItemMapper(shoppingCartItemMapper)
                .campaignDiscountCalculator(campaignDiscountCalculator)
                .restTemplate(restTemplate)
                .shoppingCartItemRepository(shoppingCartItemRepository)
                .shoppingCartRepository(shoppingCartRepository)
                .build();
    }
}


