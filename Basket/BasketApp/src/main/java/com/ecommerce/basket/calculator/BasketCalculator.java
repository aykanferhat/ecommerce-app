package com.ecommerce.basket.calculator;

import com.ecommerce.common.domain.BasketItem;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@RequiredArgsConstructor
public class BasketCalculator {

    public BigDecimal calculateBasketSalePrice(List<BasketItem> basketItems) {
        return basketItems.stream()
                .map(BasketItem::getSalePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateBasketTotalPrice(List<BasketItem> basketItems) {
        return basketItems.stream()
                .map(basketItem ->
                        calculateBasketItemTotalPrice(basketItem.getProduct().getPrice(), basketItem.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateBasketItemTotalPrice(BigDecimal productPrice, Integer quantityInt) {
        BigDecimal quantity = BigDecimal.valueOf(quantityInt);
        return productPrice.multiply(quantity);
    }

}
