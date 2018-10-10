package com.ecommerce.core.exception.faultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryFaultCode implements BusinessFaultCode {

    CATEGORY_NOT_FOUND("error.rest.category.not.found.message"),
    ;

    private final String code;
}
