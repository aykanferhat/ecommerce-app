package com.trendyol.ecommerce.discount.mapper;

import com.trendyol.ecommerce.core.domain.Coupon;
import com.trendyol.ecommerce.core.entity.CouponEntity;
import com.trendyol.ecommerce.discount.model.request.CouponCreateRequest;
import com.trendyol.ecommerce.discount.model.response.CouponCreateResponse;

public class CouponMapper {

    public CouponEntity mapToEntity(CouponCreateRequest couponCreateRequest) {

        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setDiscountType(couponCreateRequest.getDiscountType());
        couponEntity.setDiscount(couponCreateRequest.getDiscount());
        couponEntity.setThreshold(couponCreateRequest.getThreshold());
        return couponEntity;
    }

    public CouponCreateResponse mapToResponse(CouponEntity couponEntity) {

        return CouponCreateResponse.builder()
                .couponId(couponEntity.getId())
                .build();
    }

    public Coupon mapToDTO(CouponEntity couponEntity) {

        return Coupon.builder()
                .id(couponEntity.getId())
                .discount(couponEntity.getDiscount())
                .discountType(couponEntity.getDiscountType())
                .threshold(couponEntity.getThreshold())
                .build();
    }
}
