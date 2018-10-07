package com.trendyol.ecommerce.shoppingcart.calculator;

import com.trendyol.ecommerce.core.domain.ShoppingCart;
import com.trendyol.ecommerce.core.domain.ShoppingCartItem;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class ShoppingCartCalculator {

    private final ShoppingCartItemCalculator shoppingCartItemCalculator;

    public BigDecimal calculateShoppingCartTotalPrice(ShoppingCart shoppingCart) {
        List<ShoppingCartItem> shoppingCartItems = shoppingCart.getShoppingCartItems();
        return shoppingCartItems.stream()
                .map(shoppingCartItem ->
                        shoppingCartItemCalculator.calculateShoppingCartItemTotalPrice(shoppingCartItem.getProduct().getPrice(), shoppingCartItem.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateShoppingCartSalePrice(ShoppingCart shoppingCart) {
        return shoppingCart.getShoppingCartItems().stream()
                .map(ShoppingCartItem::getSalePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
