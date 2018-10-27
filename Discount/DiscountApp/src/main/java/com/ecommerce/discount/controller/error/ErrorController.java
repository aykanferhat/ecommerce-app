package com.ecommerce.discount.controller.error;

import com.ecommerce.core.model.ErrorResponse;
import com.ecommerce.core.exception.BusinessException;
import com.ecommerce.core.util.ExceptionMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ErrorController {

    private final ExceptionMessageMapper exceptionMessageMapper;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse responseException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        ErrorResponse errorResponse = exceptionMessageMapper.mapException(exception);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return errorResponse;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ErrorResponse responseException(HttpServletRequest request, HttpServletResponse response, BusinessException exception) {
        ErrorResponse errorResponse = exceptionMessageMapper.mapException(exception);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return errorResponse;
    }
}
