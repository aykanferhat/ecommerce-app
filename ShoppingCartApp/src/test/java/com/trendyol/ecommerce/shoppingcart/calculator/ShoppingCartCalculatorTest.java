package com.trendyol.ecommerce.shoppingcart.calculator;

import com.trendyol.ecommerce.core.domain.Product;
import com.trendyol.ecommerce.core.domain.ShoppingCart;
import com.trendyol.ecommerce.core.domain.ShoppingCartItem;
import org.assertj.core.util.Lists;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartCalculatorTest {

    private ShoppingCartCalculator shoppingCartCalculator;

    @Before
    public void setup(){
        ShoppingCartItemCalculator shoppingCartItemCalculator = new ShoppingCartItemCalculator();
        shoppingCartCalculator = new ShoppingCartCalculator(shoppingCartItemCalculator);
    }

    @Test
    public void calculateShoppingCartTotalPriceTest() {

        //given
        BigDecimal price1 = BigDecimal.valueOf(100);
        Integer quantity1 = 5;

        BigDecimal totalPrice1 = price1.multiply(BigDecimal.valueOf(quantity1));

        Product product1 = Product.builder()
                .price(price1)
                .build();
        ShoppingCartItem shoppingCartItem1 = ShoppingCartItem.builder()
                .product(product1)
                .quantity(quantity1)
                .build();

        BigDecimal price2 = BigDecimal.valueOf(200);
        Integer quantity2 = 10;

        Product product2 = Product.builder()
                .price(price2)
                .build();
        ShoppingCartItem shoppingCartItem2 = ShoppingCartItem.builder()
                .product(product2)
                .quantity(quantity2)
                .build();
        BigDecimal totalPrice2 = price2.multiply(BigDecimal.valueOf(quantity2));

        ShoppingCart shoppingCart = ShoppingCart.builder()
                .shoppingCartItems(Lists.newArrayList(shoppingCartItem1, shoppingCartItem2))
                .build();

        BigDecimal totalPrice = totalPrice1.add(totalPrice2);


        //when
        BigDecimal foundTotalPrice = shoppingCartCalculator.calculateShoppingCartTotalPrice(shoppingCart);

        //then
        assertEquals(totalPrice, foundTotalPrice);
    }

}
