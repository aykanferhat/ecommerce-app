package com.ecommerce.common.domain.faultcode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DiscountFaultCode implements FaultCode {

    DISCOUNT_EXCEPTION("error.rest.discount.message"),
    ;

    private final String code;

    @Override
    public String getCode() {
        return code;
    }
}
