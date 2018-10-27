package com.ecommerce.basket.service;

import com.ecommerce.basket.mapper.BasketItemMapper;
import com.ecommerce.common.domain.*;
import com.ecommerce.basket.repository.BasketItemRepository;
import com.ecommerce.basket.repository.BasketRepository;
import com.ecommerce.discount.client.CampaignServiceClient;
import com.google.common.collect.Maps;
import com.ecommerce.core.entity.BasketItemEntity;
import com.ecommerce.core.exception.BusinessException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.ecommerce.core.exception.faultCode.ProductFaultCode.PRODUCT_NOT_FOUND;
import static com.ecommerce.core.exception.faultCode.BasketFaultCode.BASKET_NOT_FOUND;

@Slf4j
@Builder
public class BasketItemServiceImpl implements BasketItemService {

    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final BasketItemMapper basketItemMapper;
    private final CampaignServiceClient campaignServiceClient;

    @Override
    public BasketItemCreateResponse createBasketItem(Long basketId, BasketItemCreateRequest basketItemCreateRequest) {

        checkBasketById(basketId);
        basketItemCreateRequest.setBasketId(basketId);
        BasketItemEntity basketItemEntity = createBasketItem(basketItemCreateRequest);
        Product product = getProductById(basketItemCreateRequest.getProductId());

        BigDecimal cartItemTotalPrice = product.getPrice().multiply(BigDecimal.valueOf(basketItemEntity.getQuantity()));
        basketItemEntity.setPrice(cartItemTotalPrice);

        BasketItemDiscountCalculatorModel basketItemModel = buildBasketItemDiscountCalculatorModel(basketItem);
        BigDecimal productDiscount = campaignServiceClient.calculateProductDiscountIfCampaignExists(basketItemModel);


        List<Campaign> campaigns = getCampaignsByCategoryIdAndQuantity(product.getCategoryId(), basketItemEntity.getQuantity());
        if (!campaigns.isEmpty()) {
            applyCampaignsIfExists(basketItemEntity, product, campaigns);
        } else {
            basketItemEntity.setSalePrice(basketItemEntity.getPrice());
            basketItemEntity.setDiscount(BigDecimal.ZERO);
        }
        BasketItemEntity createdItem = basketItemRepository.save(basketItemEntity);
        return basketItemMapper.mapToCreateResponse(createdItem);
    }

    private BasketItemEntity createBasketItem(BasketItemCreateRequest basketItemCreateRequest) {
        Long productId = basketItemCreateRequest.getProductId();
        Long basketId = basketItemCreateRequest.getBasketId();
        Integer quantity = basketItemCreateRequest.getQuantity();
        BasketItemEntity basketItemEntity;
        Optional<BasketItemEntity> optionalBasketItemEntity = basketItemRepository.findByBasketIdAndProductId(basketId, productId);
        if (optionalBasketItemEntity.isPresent()) {
            basketItemEntity = optionalBasketItemEntity.get();
            quantity += basketItemEntity.getQuantity();
            basketItemEntity.setQuantity(quantity);
        } else {
            basketItemEntity = basketItemMapper.mapToEntity(basketItemCreateRequest);
            basketItemEntity.setQuantity(quantity);
        }
        return basketItemEntity;
    }


    @Override
    public List<BasketItem> getBasketItemsByCartId(Long basketId) {
        List<BasketItemEntity> basketItemEntities = basketItemRepository.findByBasketId(basketId);
        if (basketItemEntities.isEmpty()) {
            return Lists.newArrayList();
        }
        List<BasketItem> basketItems = basketItemMapper.mapToDTOs(basketItemEntities);
        for (BasketItem basketItem : basketItems) {
            List<Campaign> campaigns = campaignServiceClient.getCampaignsByCategoryIdAndQuantity(basketItem.getProduct().getCategoryId(), basketItem.getQuantity());
            if (!campaigns.isEmpty()) {
                basketItem.setCampaigns(campaigns);
                applyCampaignsIfExists(basketItem, campaigns);
            }
        }
        return basketItems;
    }

    private void applyCampaignsIfExists(BasketItem basketItem) {


    }

    private BasketItemDiscountCalculatorModel buildBasketItemDiscountCalculatorModel(Long basketId, BasketItemCreateRequest basketItemCreateRequest) {
        return BasketItemDiscountCalculatorModel.builder()
                    .id(basketItem.getId())
                    .product()
                    .quantity(basketItemCreateRequest.getQuantity())
                    .build();
    }

    private void checkBasketById(Long basketId) {
        boolean existsBasket = basketRepository.existsById(basketId);
        if (!existsBasket) {
            throw new BusinessException(BASKET_NOT_FOUND, basketId);
        }
    }

    private Product getProductById(Long productId) {
        try {
            Map<String, String> uriParams = Maps.newHashMap();
            uriParams.put("productId", String.valueOf(productId));
            ResponseEntity<Product> responseEntity = restTemplate.exchange("http://product/api/v1/products/{productId}", HttpMethod.GET, null, Product.class, uriParams);
            return responseEntity.getBody();
        } catch (Exception exception) {
            throw new BusinessException(PRODUCT_NOT_FOUND, productId);
        }
    }
}
