package com.ecommerce.discount.controller;

import com.ecommerce.core.domain.Coupon;
import com.ecommerce.discount.model.request.CouponCreateRequest;
import com.ecommerce.discount.model.response.CouponCreateResponse;
import com.ecommerce.discount.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@ApiController
@RequestMapping(path = "/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CouponCreateResponse createCoupon(@RequestBody CouponCreateRequest couponCreateRequest) {
        CouponCreateResponse response = couponService.createCoupon(couponCreateRequest);
        setSelfAndRelLink(response);
        return response;
    }

    @GetMapping(path = "/{couponId}")
    public Coupon getCouponById(@PathVariable Long couponId) {
        return couponService.getCouponById(couponId);
    }

    @GetMapping(path = "/thresholdFilter")
    public Coupon getByThresholdOrThresholdGreater(@RequestParam BigDecimal price) {
        return couponService.getByThresholdOrThresholdGreater(price);
    }

    private void setSelfAndRelLink(ResourceSupport resourceSupport) {
        if (resourceSupport instanceof CouponCreateResponse) {
            CouponCreateResponse response = (CouponCreateResponse) resourceSupport;
            response.add(linkTo(methodOn(CouponController.class).getCouponById(response.getCouponId())).withSelfRel());
        }
    }
}
