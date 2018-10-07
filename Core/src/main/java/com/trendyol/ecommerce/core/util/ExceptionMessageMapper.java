package com.trendyol.ecommerce.core.util;

import com.trendyol.ecommerce.core.domain.ErrorResponse;
import com.trendyol.ecommerce.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Locale;

@RequiredArgsConstructor
public class ExceptionMessageMapper {

    private final MessageSource messageSource;

    public ErrorResponse mapException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getClass().getSimpleName() + ":" + exception.getMessage());
        errorResponse.setType(exception.getClass().getSimpleName());
        return errorResponse;
    }

    public ErrorResponse mapException(BusinessException businessException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setType(businessException.getClass().getSimpleName());
        errorResponse.setMessage(messageSource.getMessage(businessException.getBusinessFaultCode().getCode(), businessException.getParams(), Locale.getDefault()));
        return errorResponse;
    }

}
