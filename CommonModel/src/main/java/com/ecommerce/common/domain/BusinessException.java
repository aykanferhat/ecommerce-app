package com.ecommerce.common.domain;

import com.ecommerce.common.domain.faultcode.FaultCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String code;
    private Object[] params;

    public BusinessException(FaultCode faultCode) {
        super(faultCode.getCode());
        this.code = faultCode.getCode();
    }

    public BusinessException(FaultCode faultCode, Object... params) {
        super(faultCode.getCode());
        this.code = faultCode.getCode();
        this.params = params;
    }
}
