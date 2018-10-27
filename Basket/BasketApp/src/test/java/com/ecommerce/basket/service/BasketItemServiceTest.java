package com.ecommerce.basket.service;

import com.ecommerce.common.domain.BasketItemCreateResponse;
import com.google.common.collect.Lists;
import com.ecommerce.core.domain.Campaign;
import com.ecommerce.core.domain.Product;
import com.ecommerce.basket.model.domain.BasketItem;
import com.ecommerce.core.entity.BasketItemEntity;
import com.ecommerce.core.exception.BusinessException;
import com.ecommerce.core.util.CampaignDiscountCalculator;
import com.ecommerce.basket.mapper.BasketItemMapper;
import com.ecommerce.common.domain.BasketItemCreateRequest;
import com.ecommerce.basket.repository.BasketItemRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BasketItemServiceTest {

    @Mock
    private BasketRepository basketRepository;
    @Mock
    private BasketItemRepository basketItemRepository;
    @Mock
    private BasketItemMapper basketItemMapper;
    @Mock
    private CampaignDiscountCalculator campaignDiscountCalculator;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private BasketItemServiceImpl basketItemService;

    @Test
    public void successCreateBasketItemTest() {

        //given
        BigDecimal price = BigDecimal.valueOf(100);
        Integer quantity = 5;
        Long basketId = 1L;
        Long productId = 1L;

        Product product = Product.builder()
                .price(price)
                .build();

        BasketItemEntity basketItemEntity = new BasketItemEntity();

        BasketItemCreateRequest basketItemCreateRequest = new BasketItemCreateRequest(quantity, basketId, 1L, productId);

        when(basketRepository.existsById(basketId)).thenReturn(Boolean.TRUE);
        when(basketItemRepository.findByBasketIdAndProductId(basketId, productId)).thenReturn(Optional.empty());
        when(basketItemMapper.mapToEntity(basketItemCreateRequest)).thenReturn(basketItemEntity);

        ResponseEntity<Object> responseEntityProduct = mock(ResponseEntity.class);
        when(responseEntityProduct.getBody()).thenReturn(product);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<Object>>any(),
                any(HashMap.class)))
                .thenReturn(responseEntityProduct);


        Campaign[] campaigns = new Campaign[0];
        ResponseEntity<Campaign[]> responseEntityCampaigns = mock(ResponseEntity.class);
        when(responseEntityCampaigns.getBody()).thenReturn(campaigns);
        when(restTemplate.exchange(
                any(URI.class),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<Campaign[]>>any()))
                .thenReturn(responseEntityCampaigns);

        when(basketItemRepository.save(basketItemEntity)).thenReturn(basketItemEntity);
        when(basketItemMapper.mapToCreateResponse(basketItemEntity)).thenReturn(any(BasketItemCreateResponse.class));

        //when
        basketItemService.createBasketItem(basketId, basketItemCreateRequest);

        //then
        verify(basketItemMapper, times(1)).mapToCreateResponse(basketItemEntity);
    }

    @Test(expected = BusinessException.class)
    public void errorCreateBasketItemNotFoundBasketTest() {

        //given
        BigDecimal price = BigDecimal.valueOf(100);
        Integer quantity = 5;
        Long basketId = 1L;
        Long productId = 1L;

        BasketItemEntity basketItemEntity = new BasketItemEntity();

        BasketItemCreateRequest basketItemCreateRequest = new BasketItemCreateRequest(quantity, basketId, 1L, productId);

        when(basketRepository.existsById(basketId)).thenReturn(Boolean.FALSE);

        //when
        basketItemService.createBasketItem(basketId, basketItemCreateRequest);

        //then
        verify(basketItemMapper, times(1)).mapToCreateResponse(basketItemEntity);
    }

    @Test(expected = BusinessException.class)
    public void errorCreateBasketItemNotFoundProductTest() {

        //given
        BigDecimal price = BigDecimal.valueOf(100);
        Integer quantity = 5;
        Long basketId = 1L;
        Long productId = 1L;

        BasketItemEntity basketItemEntity = new BasketItemEntity();

        BasketItemCreateRequest basketItemCreateRequest = new BasketItemCreateRequest(quantity, basketId,  1L, productId);

        when(basketRepository.existsById(basketId)).thenReturn(Boolean.FALSE);

        //when
        basketItemService.createBasketItem(basketId, basketItemCreateRequest);

        ResponseEntity<Object> responseEntity = mock(ResponseEntity.class);
        // TODO write new test case: What will be if down Product server
        when(responseEntity.hasBody()).thenReturn(Boolean.TRUE);
        when(responseEntity.getBody()).thenReturn(Boolean.FALSE);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<Object>>any(),
                any(HashMap.class)))
                .thenReturn(responseEntity);

        //then
        verify(basketItemMapper, times(1)).mapToCreateResponse(basketItemEntity);
    }

    @Test
    public void successGetBasketItemsByBasketIdHasNotCampaignsTest() {

        //given
        Long basketId = 1L;

        Campaign[] campaigns = new Campaign[0];

        List<BasketItemEntity> basketItemEntities = Lists.newArrayList(
                new BasketItemEntity(),
                new BasketItemEntity()
        );
        List<BasketItem> basketItems = Lists.newArrayList(
                BasketItem.builder().salePrice(BigDecimal.valueOf(200))
                        .product(Product.builder().build())
                        .build(),
                BasketItem.builder().salePrice(BigDecimal.valueOf(300))
                        .product(Product.builder().build())
                        .build()
        );

        when(basketItemRepository.findByBasketId(basketId)).thenReturn(basketItemEntities);
        when(basketItemMapper.mapToDTOs(basketItemEntities)).thenReturn(basketItems);

        ResponseEntity<Campaign[]> responseEntityCampaigns = mock(ResponseEntity.class);
        when(responseEntityCampaigns.getBody()).thenReturn(campaigns);
        when(restTemplate.exchange(
                any(URI.class),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<Campaign[]>>any()))
                .thenReturn(responseEntityCampaigns);


        //when
        List<BasketItem> foundBasketItems = basketItemService.getBasketItemsByCartId(basketId);

        //then
        assertEquals(basketItems.size(), foundBasketItems.size());
    }

    @Test
    public void successGetBasketItemsByCartIdTest() {

        //given
        Long basketId = 1L;

        Campaign[] campaigns = {Campaign.builder().build()};

        List<BasketItemEntity> basketItemEntities = Lists.newArrayList(
                new BasketItemEntity(),
                new BasketItemEntity()
        );
        List<BasketItem> basketItems = Lists.newArrayList(
                BasketItem.builder().salePrice(BigDecimal.valueOf(200))
                        .product(Product.builder()
                                .build())
                        .price(BigDecimal.ONE)
                        .build(),
                BasketItem.builder().salePrice(BigDecimal.valueOf(300))
                        .product(Product.builder()
                                .build())
                        .price(BigDecimal.ONE)
                        .build()
        );

        when(basketItemRepository.findByBasketId(basketId)).thenReturn(basketItemEntities);
        when(basketItemMapper.mapToDTOs(basketItemEntities)).thenReturn(basketItems);

        ResponseEntity<Campaign[]> responseEntityCampaigns = mock(ResponseEntity.class);
        when(responseEntityCampaigns.getBody()).thenReturn(campaigns);
        when(restTemplate.exchange(
                any(URI.class),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<Campaign[]>>any()))
                .thenReturn(responseEntityCampaigns);

        when(campaignDiscountCalculator.calculateTotalDisocunt(any(), any(), any())).thenReturn(BigDecimal.ONE);

        //when
        List<BasketItem> foundBasketItems = basketItemService.getBasketItemsByCartId(basketId);

        //then
        assertEquals(basketItems.size(), foundBasketItems.size());
        verify(campaignDiscountCalculator, times(2)).calculateTotalDisocunt(any(), any(), any());
    }


}
