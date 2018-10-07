package com.trendyol.ecommerce.discount.service;

import com.trendyol.ecommerce.core.domain.Coupon;
import com.trendyol.ecommerce.discount.model.request.CouponCreateRequest;
import com.trendyol.ecommerce.discount.model.response.CouponCreateResponse;

import java.math.BigDecimal;

public interface CouponService {

    CouponCreateResponse createCoupon(CouponCreateRequest couponCreateRequest);

    Coupon getCouponById(Long couponId);

    Coupon getByThresholdOrThresholdGreater(BigDecimal totalPrice);
}
