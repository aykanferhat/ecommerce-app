package com.ecommerce.core.exception;

import com.ecommerce.core.exception.faultCode.BusinessFaultCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private BusinessFaultCode businessFaultCode;
    private Object[] params;

    public BusinessException(BusinessFaultCode businessFaultCode) {
        super(businessFaultCode.getCode());
        this.businessFaultCode = businessFaultCode;
    }

    public BusinessException(BusinessFaultCode businessFaultCode, Object... params) {
        super(businessFaultCode.getCode());
        this.businessFaultCode = businessFaultCode;
        this.params = params;
    }
}
