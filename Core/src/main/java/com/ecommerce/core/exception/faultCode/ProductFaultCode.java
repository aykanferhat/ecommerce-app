package com.ecommerce.core.exception.faultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductFaultCode implements BusinessFaultCode {

    PRODUCT_NOT_FOUND("error.rest.product.not.found.message")
    ;

    private final String code;
}
