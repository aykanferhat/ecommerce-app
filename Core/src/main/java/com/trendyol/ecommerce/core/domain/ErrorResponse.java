package com.trendyol.ecommerce.core.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String message;
    private String type;
}
