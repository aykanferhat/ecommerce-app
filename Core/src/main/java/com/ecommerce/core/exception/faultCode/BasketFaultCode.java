package com.ecommerce.core.exception.faultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BasketFaultCode implements BusinessFaultCode {

    BASKET_NOT_FOUND("error.rest.basket.cart.not.found.message")

    ;
    private final String code;
}
