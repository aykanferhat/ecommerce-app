package com.trendyol.ecommerce.discount.service;

import com.trendyol.ecommerce.core.domain.Coupon;
import com.trendyol.ecommerce.core.entity.CouponEntity;
import com.trendyol.ecommerce.core.exception.BusinessException;
import com.trendyol.ecommerce.discount.mapper.CouponMapper;
import com.trendyol.ecommerce.discount.model.request.CouponCreateRequest;
import com.trendyol.ecommerce.discount.model.response.CouponCreateResponse;
import com.trendyol.ecommerce.discount.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.trendyol.ecommerce.core.exception.faultCode.CouponFaultCode.COUPON_NOT_FOUND;

@RequiredArgsConstructor
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
