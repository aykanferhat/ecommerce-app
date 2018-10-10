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
import com.ecommerce.shoppingcart.service.*;
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
        return new ShoppingCartServiceImpl(shoppingCartRepository, shoppingCartMapper, shoppingCartItemService, shoppingCartCalculator, couponDiscountCalculator, restTemplate);
    }

    @Bean
    public ShoppingCartItemService shoppingCartItemService(ShoppingCartRepository shoppingCartRepository,
                                                           ShoppingCartItemRepository shoppingCartItemRepository,
                                                           ShoppingCartItemMapper shoppingCartItemMapper,
                                                           CampaignDiscountCalculator campaignDiscountCalculator,
                                                           RestTemplate restTemplate) {
        return new ShoppingCartItemServiceImpl(shoppingCartRepository, shoppingCartItemRepository, shoppingCartItemMapper, campaignDiscountCalculator, restTemplate);
    }
}


