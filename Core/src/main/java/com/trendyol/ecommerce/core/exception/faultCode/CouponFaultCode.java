package com.trendyol.ecommerce.core.exception.faultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponFaultCode implements BusinessFaultCode {

    COUPON_NOT_FOUND("error.rest.coupon.not.found.message"),
    ;

    private final String code;
}
