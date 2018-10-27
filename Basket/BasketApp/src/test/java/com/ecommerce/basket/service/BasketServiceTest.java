package com.ecommerce.basket.service;

import com.ecommerce.common.domain.BasketCreateRequest;
import com.ecommerce.common.domain.BasketCreateResponse;
import com.google.common.collect.Lists;
import com.ecommerce.core.domain.Coupon;
import com.ecommerce.basket.model.domain.Basket;
import com.ecommerce.basket.model.domain.BasketItem;
import com.ecommerce.core.entity.BasketEntity;
import com.ecommerce.core.exception.BusinessException;
import com.ecommerce.core.util.CouponDiscountCalculator;
import com.ecommerce.basket.calculator.BasketCalculator;
import com.ecommerce.basket.mapper.BasketMapper;
import com.ecommerce.basket.repository.BasketRepository;
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
public class BasketServiceTest {

    @Mock
    private BasketRepository basketRepository;
    @Mock
    private BasketMapper basketMapper;
    @Mock
    private BasketItemService basketItemService;
    @Mock
    private CouponDiscountCalculator couponDiscountCalculator;
    @Mock
    private BasketCalculator basketCalculator;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private BasketServiceImpl basketService;

    @Test
    public void successCreateBasketTest() {

        //given
        Long userId = 1L;

        BasketCreateRequest basketCreateRequest = new BasketCreateRequest(userId);
        BasketEntity basketEntity = new BasketEntity();

        when(basketMapper.mapToEntity(basketCreateRequest)).thenReturn(basketEntity);
        when(basketRepository.save(basketEntity)).thenReturn(basketEntity);
        when(basketMapper.mapToResponse(basketEntity)).thenReturn(any(BasketCreateResponse.class));

        //when
        basketService.createBasket(basketCreateRequest);

        //then
        verify(basketMapper, times(1)).mapToResponse(any(BasketEntity.class));
    }

    @Test(expected = BusinessException.class)
    public void errorCreateBasketTest() {

        //given
        Long userId = null;

        BasketCreateRequest basketCreateRequest = new BasketCreateRequest(userId);

        //when
        basketService.createBasket(basketCreateRequest);

        //then
        verify(basketMapper, times(1)).mapToResponse(any(BasketEntity.class));
    }

    @Test
    public void successGetBasketByIdHasNotCouponTest() {
        //given
        Long basketId = 1L;

        BasketEntity basketEntity = new BasketEntity();
        Basket basket = Basket.builder().build();

        when(basketRepository.findById(basketId)).thenReturn(Optional.of(basketEntity));
        when(basketMapper.mapToDTO(basketEntity)).thenReturn(basket);
        when(basketCalculator.calculateBasketTotalPrice(anyList())).thenReturn(BigDecimal.valueOf(100));
        when(basketCalculator.calculateBasketSalePrice(anyList())).thenReturn(BigDecimal.valueOf(150));


        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(null);
        when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(), ArgumentMatchers.<Class<Object>>any())).thenReturn(responseEntity);

        //when
        Basket foundBasket = basketService.getBasketById(basketId);

        //then

        assertEquals(BigDecimal.valueOf(100), foundBasket.getTotalPrice());
        assertEquals(BigDecimal.valueOf(150), foundBasket.getSalePrice());
    }


    @Test
    public void successGetBasketByIdTest() {

        //given
        Long basketId = 1L;

        BasketEntity basketEntity = new BasketEntity();
        Basket basket = Basket.builder()
                .totalPrice(BigDecimal.ONE)
                .salePrice(BigDecimal.ONE)
                .build();
        List<BasketItem> basketItems = Lists.newArrayList(
                BasketItem.builder().quantity(10).build(),
                BasketItem.builder().quantity(10).build()
        );
        Coupon coupon = Coupon.builder().build();

        when(basketRepository.findById(basketId))
                .thenReturn(Optional.of(basketEntity));

        when(basketMapper.mapToDTO(basketEntity))
                .thenReturn(basket);

        when(basketCalculator.calculateBasketTotalPrice(anyList()))
                .thenReturn(BigDecimal.valueOf(100));

        when(basketCalculator.calculateBasketSalePrice(anyList()))
                .thenReturn(BigDecimal.valueOf(150));

        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        // TODO write a new test case: What will be if down Product server
        when(responseEntity.getBody()).thenReturn(coupon);
        when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(), ArgumentMatchers.<Class<Object>>any())).thenReturn(responseEntity);

        when(couponDiscountCalculator.calculateCouponDiscount(any(), any())).thenReturn(BigDecimal.valueOf(150));

        //when
        Basket foundBasket = basketService.getBasketById(basketId);

        //then

        assertEquals(BigDecimal.valueOf(100), foundBasket.getTotalPrice());
        assertEquals(BigDecimal.valueOf(100), foundBasket.getDiscount());
    }
}
