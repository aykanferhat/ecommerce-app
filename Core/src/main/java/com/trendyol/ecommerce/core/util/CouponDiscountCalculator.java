package com.trendyol.ecommerce.core.util;

import com.trendyol.ecommerce.core.domain.ShoppingCart;

public interface CouponDiscountCalculator {

    void applyCouponDiscount(ShoppingCart shoppingCart);
}
