package com.ecommerce.discount.service;

import com.ecommerce.common.domain.Coupon;
import com.ecommerce.common.domain.CouponCreateRequest;
import com.ecommerce.common.domain.CouponCreateResponse;
import com.ecommerce.discount.repository.CouponRepository;
import com.ecommerce.core.entity.CouponEntity;
import com.ecommerce.core.exception.BusinessException;
import com.ecommerce.discount.mapper.CouponMapper;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.ecommerce.core.exception.faultCode.CouponFaultCode.COUPON_NOT_FOUND;

@Builder
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @Override
    public CouponCreateResponse createCoupon(CouponCreateRequest couponCreateRequest) {
        CouponEntity couponEntity = couponRepository.save(couponMapper.mapToEntity(couponCreateRequest));
        return couponMapper.mapToResponse(couponEntity);
    }

    @Override
    public Coupon getCouponById(Long couponId) {
        Optional<CouponEntity> optionalCouponEntity = couponRepository.findById(couponId);
        if (!optionalCouponEntity.isPresent()) {
            throw new BusinessException(COUPON_NOT_FOUND);
        }
        CouponEntity couponEntity = optionalCouponEntity.get();
        return couponMapper.mapToDTO(couponEntity);
    }

    @Override
    public Coupon getByThresholdOrThresholdGreater(BigDecimal totalPrice) {
        List<CouponEntity> couponEntities = couponRepository.findByThresholdOrThresholdGreaterThan(totalPrice);
        return couponEntities.stream().findFirst().map(couponMapper::mapToDTO).orElse(null);
    }


}
