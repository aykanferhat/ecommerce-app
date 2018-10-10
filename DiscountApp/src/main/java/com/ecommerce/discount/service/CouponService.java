package com.ecommerce.discount.service;

import com.ecommerce.core.domain.Coupon;
import com.ecommerce.discount.model.request.CouponCreateRequest;
import com.ecommerce.discount.model.response.CouponCreateResponse;

import java.math.BigDecimal;

public interface CouponService {

    CouponCreateResponse createCoupon(CouponCreateRequest couponCreateRequest);

    Coupon getCouponById(Long couponId);

    Coupon getByThresholdOrThresholdGreater(BigDecimal totalPrice);
}
