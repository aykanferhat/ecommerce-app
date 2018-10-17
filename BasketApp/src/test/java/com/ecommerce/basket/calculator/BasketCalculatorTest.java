package com.ecommerce.basket.calculator;

import com.ecommerce.core.domain.Product;
import com.ecommerce.core.domain.Basket;
import com.ecommerce.core.domain.BasketItem;
import org.assertj.core.util.Lists;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BasketCalculatorTest {

    private BasketCalculator basketCalculator;

    @Before
    public void setup(){
        basketCalculator = new BasketCalculator();
    }

    @Test
    public void calculateBasketTotalPriceTest() {

        //given
        BigDecimal price1 = BigDecimal.valueOf(100);
        Integer quantity1 = 5;

        BigDecimal totalPrice1 = price1.multiply(BigDecimal.valueOf(quantity1));

        Product product1 = Product.builder()
                .price(price1)
                .build();
        BasketItem basketItem1 = BasketItem.builder()
                .product(product1)
                .quantity(quantity1)
                .build();

        BigDecimal price2 = BigDecimal.valueOf(200);
        Integer quantity2 = 10;

        Product product2 = Product.builder()
                .price(price2)
                .build();
        BasketItem basketItem2 = BasketItem.builder()
                .product(product2)
                .quantity(quantity2)
                .build();
        BigDecimal totalPrice2 = price2.multiply(BigDecimal.valueOf(quantity2));

        Basket basket = Basket.builder()
                .basketItems(Lists.newArrayList(basketItem1, basketItem2))
                .build();

        BigDecimal totalPrice = totalPrice1.add(totalPrice2);


        //when
        BigDecimal foundTotalPrice = basketCalculator.calculateBasketTotalPrice(basket.getBasketItems());

        //then
        assertEquals(totalPrice, foundTotalPrice);
    }

}
