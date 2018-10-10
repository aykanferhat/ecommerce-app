package com.ecommerce.shoppingcart.calculator;

import com.ecommerce.core.domain.ShoppingCartItem;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@RequiredArgsConstructor
public class ShoppingCartCalculator {

    public BigDecimal calculateShoppingCartSalePrice(List<ShoppingCartItem> shoppingCartItems) {
        return shoppingCartItems.stream()
                .map(ShoppingCartItem::getSalePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateShoppingCartTotalPrice(List<ShoppingCartItem> shoppingCartItems) {
        return shoppingCartItems.stream()
                .map(shoppingCartItem ->
                        calculateShoppingCartItemTotalPrice(shoppingCartItem.getProduct().getPrice(), shoppingCartItem.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateShoppingCartItemTotalPrice(BigDecimal productPrice, Integer quantityInt) {
        BigDecimal quantity = BigDecimal.valueOf(quantityInt);
        return productPrice.multiply(quantity);
    }

}
