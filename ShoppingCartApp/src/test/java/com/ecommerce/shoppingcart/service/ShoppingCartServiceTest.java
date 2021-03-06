package com.ecommerce.shoppingcart.service;

import com.ecommerce.shoppingcart.model.request.ShoppingCartCreateRequest;
import com.ecommerce.shoppingcart.model.response.ShoppingCartCreateResponse;
import com.google.common.collect.Lists;
import com.ecommerce.core.domain.Coupon;
import com.ecommerce.core.domain.ShoppingCart;
import com.ecommerce.core.domain.ShoppingCartItem;
import com.ecommerce.core.entity.ShoppingCartEntity;
import com.ecommerce.core.exception.BusinessException;
import com.ecommerce.core.util.CouponDiscountCalculator;
import com.ecommerce.shoppingcart.calculator.ShoppingCartCalculator;
import com.ecommerce.shoppingcart.mapper.ShoppingCartMapper;
import com.ecommerce.shoppingcart.repository.ShoppingCartRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartMapper shoppingCartMapper;
    @Mock
    private ShoppingCartItemService shoppingCartItemService;
    @Mock
    private CouponDiscountCalculator couponDiscountCalculator;
    @Mock
    private ShoppingCartCalculator shoppingCartCalculator;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Test
    public void successCreateShoppingCartTest() {

        //given
        Long userId = 1L;

        ShoppingCartCreateRequest shoppingCartCreateRequest = new ShoppingCartCreateRequest(userId);
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();

        when(shoppingCartMapper.mapToEntity(shoppingCartCreateRequest)).thenReturn(shoppingCartEntity);
        when(shoppingCartRepository.save(shoppingCartEntity)).thenReturn(shoppingCartEntity);
        when(shoppingCartMapper.mapToResponse(shoppingCartEntity)).thenReturn(any(ShoppingCartCreateResponse.class));

        //when
        shoppingCartService.createShoppingCart(shoppingCartCreateRequest);

        //then
        verify(shoppingCartMapper, times(1)).mapToResponse(any(ShoppingCartEntity.class));
    }

    @Test(expected = BusinessException.class)
    public void errorCreateShoppingCartTest() {

        //given
        Long userId = null;

        ShoppingCartCreateRequest shoppingCartCreateRequest = new ShoppingCartCreateRequest(userId);

        //when
        shoppingCartService.createShoppingCart(shoppingCartCreateRequest);

        //then
        verify(shoppingCartMapper, times(1)).mapToResponse(any(ShoppingCartEntity.class));
    }

    @Test
    public void successGetShoppingCartByIdHasNotCouponTest() {
        //given
        Long shoppingCartId = 1L;

        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        ShoppingCart shoppingCart = ShoppingCart.builder().build();

        when(shoppingCartRepository.findById(shoppingCartId)).thenReturn(Optional.of(shoppingCartEntity));
        when(shoppingCartMapper.mapToDTO(shoppingCartEntity)).thenReturn(shoppingCart);
        when(shoppingCartCalculator.calculateShoppingCartTotalPrice(anyList())).thenReturn(BigDecimal.valueOf(100));
        when(shoppingCartCalculator.calculateShoppingCartSalePrice(anyList())).thenReturn(BigDecimal.valueOf(150));


        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(null);
        when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(), ArgumentMatchers.<Class<Object>>any())).thenReturn(responseEntity);

        //when
        ShoppingCart foundShoppingCart = shoppingCartService.getShoppingCartById(shoppingCartId);

        //then

        assertEquals(BigDecimal.valueOf(100), foundShoppingCart.getTotalPrice());
        assertEquals(BigDecimal.valueOf(150), foundShoppingCart.getSalePrice());
    }


    @Test
    public void successGetShoppingCartByIdTest() {

        //given
        Long shoppingCartId = 1L;

        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .totalPrice(BigDecimal.ONE)
                .salePrice(BigDecimal.ONE)
                .build();
        List<ShoppingCartItem> shoppingCartItems = Lists.newArrayList(
                ShoppingCartItem.builder().quantity(10).build(),
                ShoppingCartItem.builder().quantity(10).build()
        );
        Coupon coupon = Coupon.builder().build();

        when(shoppingCartRepository.findById(shoppingCartId))
                .thenReturn(Optional.of(shoppingCartEntity));

        when(shoppingCartMapper.mapToDTO(shoppingCartEntity))
                .thenReturn(shoppingCart);

        when(shoppingCartCalculator.calculateShoppingCartTotalPrice(anyList()))
                .thenReturn(BigDecimal.valueOf(100));

        when(shoppingCartCalculator.calculateShoppingCartSalePrice(anyList()))
                .thenReturn(BigDecimal.valueOf(150));

        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        // TODO write a new test case: What will be if down Product server
        when(responseEntity.getBody()).thenReturn(coupon);
        when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(), ArgumentMatchers.<Class<Object>>any())).thenReturn(responseEntity);

        when(couponDiscountCalculator.calculateCouponDiscount(any(), any())).thenReturn(BigDecimal.valueOf(150));

        //when
        ShoppingCart foundShoppingCart = shoppingCartService.getShoppingCartById(shoppingCartId);

        //then

        assertEquals(BigDecimal.valueOf(100), foundShoppingCart.getTotalPrice());
        assertEquals(BigDecimal.valueOf(100), foundShoppingCart.getDiscount());
    }
}
