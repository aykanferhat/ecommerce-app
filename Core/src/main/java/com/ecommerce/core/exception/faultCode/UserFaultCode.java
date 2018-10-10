package com.ecommerce.core.exception.faultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  UserFaultCode implements BusinessFaultCode {

    USER_NOT_FOUND("error.rest.user.not.found.message")
    ;

    private final String code;

}
