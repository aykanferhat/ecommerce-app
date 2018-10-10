package com.ecommerce.discount.repository;

import com.ecommerce.core.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    @Query("select coupon from CouponEntity coupon where coupon.threshold < :threshold or coupon.threshold = :threshold order by threshold asc")
    List<CouponEntity> findByThresholdOrThresholdGreaterThan(@Param("threshold") BigDecimal threshold);

}
