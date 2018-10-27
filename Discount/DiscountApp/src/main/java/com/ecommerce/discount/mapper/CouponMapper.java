package com.ecommerce.discount.mapper;

import com.ecommerce.common.domain.Coupon;
import com.ecommerce.common.domain.CouponCreateRequest;
import com.ecommerce.common.domain.CouponCreateResponse;
import com.ecommerce.common.domain.DiscountType;
import com.ecommerce.core.entity.CouponEntity;

public class CouponMapper {

    public CouponEntity mapToEntity(CouponCreateRequest couponCreateRequest) {

        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setDiscountType(couponCreateRequest.getDiscountType().getValue());
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
                .discountType(DiscountType.valueOf(couponEntity.getDiscountType()))
                .threshold(couponEntity.getThreshold())
                .build();
    }
}
