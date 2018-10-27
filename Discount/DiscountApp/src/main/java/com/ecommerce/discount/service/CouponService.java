package com.ecommerce.discount.service;

import com.ecommerce.common.domain.Coupon;
import com.ecommerce.common.domain.CouponCreateRequest;
import com.ecommerce.common.domain.CouponCreateResponse;

import java.math.BigDecimal;

public interface CouponService {

    CouponCreateResponse createCoupon(CouponCreateRequest couponCreateRequest);

    Coupon getCouponById(Long couponId);

    Coupon getByThresholdOrThresholdGreater(BigDecimal totalPrice);
}
