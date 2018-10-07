package com.trendyol.ecommerce.core.util;

import com.trendyol.ecommerce.core.domain.Campaign;
import com.trendyol.ecommerce.core.domain.Product;
import com.trendyol.ecommerce.core.domain.ShoppingCartItem;
import com.trendyol.ecommerce.core.entity.ShoppingCartItemEntity;

import java.util.List;

public interface CampaignDiscountCalculator {

    void applyCampaignDiscount(ShoppingCartItem shoppingCartItem);

    void applyCampaignDiscount(ShoppingCartItemEntity shoppingCartItemEntity, Product product, List<Campaign> campaigns);

}
