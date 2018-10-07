package com.trendyol.ecommerce.shoppingcart.calculator;

import java.math.BigDecimal;

public class ShoppingCartItemCalculator {

    public BigDecimal calculateShoppingCartItemTotalPrice(BigDecimal productPrice, Integer quantityInt) {
        BigDecimal quantity = BigDecimal.valueOf(quantityInt);
        return productPrice.multiply(quantity);
    }

}
