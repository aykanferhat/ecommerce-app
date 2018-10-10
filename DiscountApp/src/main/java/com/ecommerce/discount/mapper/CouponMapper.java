package com.ecommerce.discount.mapper;

import com.ecommerce.discount.model.request.CouponCreateRequest;
import com.ecommerce.discount.model.response.CouponCreateResponse;
import com.ecommerce.core.domain.Coupon;
import com.ecommerce.core.entity.CouponEntity;

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
