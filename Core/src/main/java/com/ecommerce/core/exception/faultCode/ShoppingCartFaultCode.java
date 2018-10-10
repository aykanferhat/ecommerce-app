package com.ecommerce.core.exception.faultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  ShoppingCartFaultCode implements BusinessFaultCode {

    SHOPPING_CART_NOT_FOUND("error.rest.shopping.cart.not.found.message")

    ;
    private final String code;
}
