package com.ecommerce.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiscountType {

    RATE("RATE"), AMOUNT("AMOUNT");

    private final String value;

    public boolean isRate() {
        return this == RATE;
    }

    public boolean isAmount() {
        return this == AMOUNT;
    }

}