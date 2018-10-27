package com.ecommerce.common.domain.faultcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponFaultCode implements FaultCode{

    COUPON_NOT_FOUND("error.rest.coupon.not.found.message"),
    ;

    private final String code;
}
