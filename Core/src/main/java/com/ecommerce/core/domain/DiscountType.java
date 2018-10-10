package com.ecommerce.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiscountType {

    RATE, AMOUNT;

    public boolean isRate() {
        return this == RATE;
    }

    public boolean isAmount() {
        return this == AMOUNT;
    }

}