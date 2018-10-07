package com.trendyol.ecommerce.shoppingcart.configuration;

import com.trendyol.ecommerce.core.util.CampaignDiscountCalculator;
import com.trendyol.ecommerce.core.util.CouponDiscountCalculator;
import com.trendyol.ecommerce.shoppingcart.calculator.ShoppingCartCalculator;
import com.trendyol.ecommerce.shoppingcart.calculator.ShoppingCartItemCalculator;
import com.trendyol.ecommerce.shoppingcart.mapper.ShoppingCartItemMapper;
import com.trendyol.ecommerce.shoppingcart.mapper.ShoppingCartMapper;
import com.trendyol.ecommerce.shoppingcart.repository.ShoppingCartItemRepository;
import com.trendyol.ecommerce.shoppingcart.repository.ShoppingCartRepository;
import com.trendyol.ecommerce.shoppingcart.service.*;
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
        return new ShoppingCartServiceImpl(shoppingCartRepository, shoppingCartMapper, shoppingCartItemService, couponDiscountCalculator, shoppingCartCalculator, restTemplate);
    }

    @Bean
    public ShoppingCartItemService shoppingCartItemService(ShoppingCartRepository shoppingCartRepository,
                                                           ShoppingCartItemRepository shoppingCartItemRepository,
                                                           ShoppingCartItemMapper shoppingCartItemMapper,
                                                           CampaignDiscountCalculator campaignDiscountCalculator,
                                                           ShoppingCartItemCalculator shoppingCartItemCalculator,
                                                           RestTemplate restTemplate) {
        return new ShoppingCartItemServiceImpl(shoppingCartRepository, shoppingCartItemRepository, shoppingCartItemMapper, campaignDiscountCalculator, shoppingCartItemCalculator, restTemplate);
    }
}


