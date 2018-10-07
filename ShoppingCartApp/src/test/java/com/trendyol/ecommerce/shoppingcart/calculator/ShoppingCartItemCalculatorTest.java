package com.trendyol.ecommerce.shoppingcart.calculator;

import com.trendyol.ecommerce.core.domain.Product;
import com.trendyol.ecommerce.core.domain.ShoppingCartItem;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartItemCalculatorTest {

    private ShoppingCartItemCalculator shoppingCartItemCalculator;

    @Before
    public void setup(){
        shoppingCartItemCalculator = new ShoppingCartItemCalculator();
    }

    @Test
    public void successCalculateShoppingCartItemTotalPriceTest() {

        //given
        BigDecimal price = BigDecimal.valueOf(100);
        Integer quantity = 5;

        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));

        Product product = Product.builder()
                .price(price)
                .build();
        ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder()
                .product(product)
                .quantity(quantity)
                .build();

        //when
        BigDecimal foundTotalPrice = shoppingCartItemCalculator.calculateShoppingCartItemTotalPrice(shoppingCartItem.getProduct().getPrice(), shoppingCartItem.getQuantity());

        //then
        assertEquals(totalPrice, foundTotalPrice);
    }




}
