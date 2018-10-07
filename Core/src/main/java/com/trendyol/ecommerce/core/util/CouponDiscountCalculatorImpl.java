package com.trendyol.ecommerce.core.util;

import com.trendyol.ecommerce.core.domain.Coupon;
import com.trendyol.ecommerce.core.domain.DiscountType;
import com.trendyol.ecommerce.core.domain.ShoppingCart;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CouponDiscountCalculatorImpl implements CouponDiscountCalculator {

    @Override
    public void applyCouponDiscount(ShoppingCart shoppingCart) {
        Coupon coupon = shoppingCart.getCoupon();
        BigDecimal discountAmount = BigDecimal.ZERO;
        DiscountType discountType = coupon.getDiscountType();
        if (discountType.isRate()) {
            discountAmount = shoppingCart.getSalePrice().multiply(coupon.getDiscount()).divide(BigDecimal.valueOf(100));
        } else if (discountType.isAmount()) {
            discountAmount = coupon.getDiscount();
        }
        // item campaigns discount and coupon discount
        BigDecimal totalDiscount = shoppingCart.getTotalPrice().subtract(shoppingCart.getSalePrice()).add(discountAmount);
        shoppingCart.setDiscount(totalDiscount);

        BigDecimal totalSale = shoppingCart.getTotalPrice().subtract(totalDiscount);
        shoppingCart.setSalePrice(totalSale);

    }
}
