package com.trendyol.ecommerce.shoppingcart.service;

import com.google.common.collect.Lists;
import com.trendyol.ecommerce.core.domain.Campaign;
import com.trendyol.ecommerce.core.domain.Product;
import com.trendyol.ecommerce.core.domain.ShoppingCartItem;
import com.trendyol.ecommerce.core.entity.ShoppingCartItemEntity;
import com.trendyol.ecommerce.core.exception.BusinessException;
import com.trendyol.ecommerce.core.util.CampaignDiscountCalculator;
import com.trendyol.ecommerce.shoppingcart.calculator.ShoppingCartItemCalculator;
import com.trendyol.ecommerce.shoppingcart.mapper.ShoppingCartItemMapper;
import com.trendyol.ecommerce.shoppingcart.model.request.ShoppingCartItemCreateRequest;
import com.trendyol.ecommerce.shoppingcart.model.response.ShoppingCartItemCreateResponse;
import com.trendyol.ecommerce.shoppingcart.repository.ShoppingCartItemRepository;
import com.trendyol.ecommerce.shoppingcart.repository.ShoppingCartRepository;
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
public class ShoppingCartItemServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;
    @Mock
    private ShoppingCartItemMapper shoppingCartItemMapper;
    @Mock
    private CampaignDiscountCalculator campaignDiscountCalculator;
    @Mock
    private ShoppingCartItemCalculator shoppingCartItemCalculator;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private ShoppingCartItemServiceImpl shoppingCartItemService;

    @Test
    public void successCreateShoppingCartItemTest() {

        //given
        BigDecimal price = BigDecimal.valueOf(100);
        Integer quantity = 5;
        Long shoppingCartId = 1L;
        Long productId = 1L;

        Product product = Product.builder().build();

        ShoppingCartItemEntity shoppingCartItemEntity = new ShoppingCartItemEntity();

        ShoppingCartItemCreateRequest shoppingCartItemCreateRequest = new ShoppingCartItemCreateRequest(quantity, shoppingCartId, productId);

        when(shoppingCartRepository.existsById(shoppingCartId)).thenReturn(Boolean.TRUE);
        when(shoppingCartItemRepository.findByShoppingCartIdAndProductId(shoppingCartId, productId)).thenReturn(Optional.empty());
        when(shoppingCartItemMapper.mapToEntity(shoppingCartItemCreateRequest)).thenReturn(shoppingCartItemEntity);

        ResponseEntity<Object> responseEntityProduct = mock(ResponseEntity.class);
        when(responseEntityProduct.getBody()).thenReturn(product);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<Object>>any(),
                any(HashMap.class)))
                .thenReturn(responseEntityProduct);

        BigDecimal cartItemTotalPrice = BigDecimal.valueOf(100);
        when(shoppingCartItemCalculator.calculateShoppingCartItemTotalPrice(product.getPrice(), quantity)).thenReturn(cartItemTotalPrice);

        Campaign[] campaigns = new Campaign[0];
        ResponseEntity<Campaign[]> responseEntityCampaigns = mock(ResponseEntity.class);
        when(responseEntityCampaigns.getBody()).thenReturn(campaigns);
        when(restTemplate.exchange(
                any(URI.class),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<Campaign[]>>any()))
                .thenReturn(responseEntityCampaigns);

        when(shoppingCartItemRepository.save(shoppingCartItemEntity)).thenReturn(shoppingCartItemEntity);
        when(shoppingCartItemMapper.mapToCreateResponse(shoppingCartItemEntity)).thenReturn(any(ShoppingCartItemCreateResponse.class));

        //when
        shoppingCartItemService.createShoppingCartItem(shoppingCartId, shoppingCartItemCreateRequest);

        //then
        verify(shoppingCartItemMapper, times(1)).mapToCreateResponse(shoppingCartItemEntity);
    }

    @Test(expected = BusinessException.class)
    public void errorCreateShoppingCartItemNotFoundShoppingCartTest() {

        //given
        BigDecimal price = BigDecimal.valueOf(100);
        Integer quantity = 5;
        Long shoppingCartId = 1L;
        Long productId = 1L;

        ShoppingCartItemEntity shoppingCartItemEntity = new ShoppingCartItemEntity();

        ShoppingCartItemCreateRequest shoppingCartItemCreateRequest = new ShoppingCartItemCreateRequest(quantity, shoppingCartId, productId);

        when(shoppingCartRepository.existsById(shoppingCartId)).thenReturn(Boolean.FALSE);

        //when
        shoppingCartItemService.createShoppingCartItem(shoppingCartId, shoppingCartItemCreateRequest);

        //then
        verify(shoppingCartItemMapper, times(1)).mapToCreateResponse(shoppingCartItemEntity);
    }

    @Test(expected = BusinessException.class)
    public void errorCreateShoppingCartItemNotFoundProductTest() {

        //given
        BigDecimal price = BigDecimal.valueOf(100);
        Integer quantity = 5;
        Long shoppingCartId = 1L;
        Long productId = 1L;

        ShoppingCartItemEntity shoppingCartItemEntity = new ShoppingCartItemEntity();

        ShoppingCartItemCreateRequest shoppingCartItemCreateRequest = new ShoppingCartItemCreateRequest(quantity, shoppingCartId, productId);

        when(shoppingCartRepository.existsById(shoppingCartId)).thenReturn(Boolean.FALSE);

        //when
        shoppingCartItemService.createShoppingCartItem(shoppingCartId, shoppingCartItemCreateRequest);

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
        verify(shoppingCartItemMapper, times(1)).mapToCreateResponse(shoppingCartItemEntity);
    }

    @Test
    public void successGetShoppingCartItemsByCartIdHasNotCampaignsTest() {

        //given
        Long shoppingCartId = 1L;

        Campaign[] campaigns = new Campaign[0];

        List<ShoppingCartItemEntity> shoppingCartItemEntities = Lists.newArrayList(
                new ShoppingCartItemEntity(),
                new ShoppingCartItemEntity()
        );
        List<ShoppingCartItem> shoppingCartItems = Lists.newArrayList(
                ShoppingCartItem.builder().salePrice(BigDecimal.valueOf(200))
                        .product(Product.builder().build())
                        .build(),
                ShoppingCartItem.builder().salePrice(BigDecimal.valueOf(300))
                        .product(Product.builder().build())
                        .build()
        );

        when(shoppingCartItemRepository.findByShoppingCartId(shoppingCartId)).thenReturn(shoppingCartItemEntities);
        when(shoppingCartItemMapper.mapToDTOs(shoppingCartItemEntities)).thenReturn(shoppingCartItems);

        ResponseEntity<Campaign[]> responseEntityCampaigns = mock(ResponseEntity.class);
        when(responseEntityCampaigns.getBody()).thenReturn(campaigns);
        when(restTemplate.exchange(
                any(URI.class),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<Campaign[]>>any()))
                .thenReturn(responseEntityCampaigns);


        //when
        List<ShoppingCartItem> foundShoppingCartItems = shoppingCartItemService.getShoppingCartItemsByCartId(shoppingCartId);

        //then
        assertEquals(shoppingCartItems.size(), foundShoppingCartItems.size());
    }

    @Test
    public void successGetShoppingCartItemsByCartIdTest() {

        //given
        Long shoppingCartId = 1L;

        Campaign[] campaigns = {Campaign.builder().build()};

        List<ShoppingCartItemEntity> shoppingCartItemEntities = Lists.newArrayList(
                new ShoppingCartItemEntity(),
                new ShoppingCartItemEntity()
        );
        List<ShoppingCartItem> shoppingCartItems = Lists.newArrayList(
                ShoppingCartItem.builder().salePrice(BigDecimal.valueOf(200))
                        .product(Product.builder().build())
                        .build(),
                ShoppingCartItem.builder().salePrice(BigDecimal.valueOf(300))
                        .product(Product.builder().build())
                        .build()
        );

        when(shoppingCartItemRepository.findByShoppingCartId(shoppingCartId)).thenReturn(shoppingCartItemEntities);
        when(shoppingCartItemMapper.mapToDTOs(shoppingCartItemEntities)).thenReturn(shoppingCartItems);

        ResponseEntity<Campaign[]> responseEntityCampaigns = mock(ResponseEntity.class);
        when(responseEntityCampaigns.getBody()).thenReturn(campaigns);
        when(restTemplate.exchange(
                any(URI.class),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<Campaign[]>>any()))
                .thenReturn(responseEntityCampaigns);


        for (ShoppingCartItem shoppingCartItem: shoppingCartItems) {
            doNothing().when(campaignDiscountCalculator).applyCampaignDiscount(shoppingCartItem);
        }

        //when
        List<ShoppingCartItem> foundShoppingCartItems = shoppingCartItemService.getShoppingCartItemsByCartId(shoppingCartId);

        //then
        assertEquals(shoppingCartItems.size(), foundShoppingCartItems.size());
        verify(campaignDiscountCalculator, times(2)).applyCampaignDiscount(any());
    }


}
