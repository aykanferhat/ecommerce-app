package com.ecommerce.basket.service;

import com.ecommerce.basket.mapper.BasketItemMapper;
import com.ecommerce.basket.model.request.BasketItemCreateRequest;
import com.ecommerce.basket.model.response.BasketItemCreateResponse;
import com.ecommerce.basket.repository.BasketItemRepository;
import com.ecommerce.basket.repository.BasketRepository;
import com.google.common.collect.Maps;
import com.ecommerce.core.domain.Campaign;
import com.ecommerce.core.domain.Product;
import com.ecommerce.core.domain.BasketItem;
import com.ecommerce.core.entity.BasketItemEntity;
import com.ecommerce.core.exception.BusinessException;
import com.ecommerce.core.util.CampaignDiscountCalculator;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
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
    private final CampaignDiscountCalculator campaignDiscountCalculator;
    private final RestTemplate restTemplate;

    @Override
    public BasketItemCreateResponse createBasketItem(Long basketId, BasketItemCreateRequest basketItemCreateRequest) {

        checkBasketById(basketId);
        basketItemCreateRequest.setBasketId(basketId);
        BasketItemEntity basketItemEntity = createBasketItem(basketItemCreateRequest);
        Product product = getProductById(basketItemCreateRequest.getProductId());

        BigDecimal cartItemTotalPrice = product.getPrice().multiply(BigDecimal.valueOf(basketItemEntity.getQuantity()));
        basketItemEntity.setPrice(cartItemTotalPrice);
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
            List<Campaign> campaigns = getCampaignsByCategoryIdAndQuantity(basketItem.getProduct().getCategoryId(), basketItem.getQuantity());
            if (!campaigns.isEmpty()) {
                basketItem.setCampaigns(campaigns);
                applyCampaignsIfExists(basketItem, campaigns);
            }
        }
        return basketItems;
    }

    private void applyCampaignsIfExists(BasketItem basketItem, List<Campaign> campaigns) {
        BigDecimal totalDiscountAmount = campaignDiscountCalculator.calculateTotalDisocunt(basketItem.getProduct(), basketItem.getQuantity(), campaigns);
        basketItem.setDiscount(totalDiscountAmount);
        BigDecimal salePrice = basketItem.getPrice().subtract(totalDiscountAmount);
        basketItem.setSalePrice(salePrice);
    }

    private void applyCampaignsIfExists(BasketItemEntity basketItemEntity, Product product, List<Campaign> campaigns) {
        BigDecimal totalDisocunt = campaignDiscountCalculator.calculateTotalDisocunt(product, basketItemEntity.getQuantity(), campaigns);
        basketItemEntity.setDiscount(totalDisocunt);
        BigDecimal salePrice = basketItemEntity.getPrice().subtract(totalDisocunt);
        basketItemEntity.setSalePrice(salePrice);
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

    private List<Campaign> getCampaignsByCategoryIdAndQuantity(Long categoryId, Integer quantity) {
        URI uri = UriComponentsBuilder.fromUriString("http://discount/api/v1/campaigns/exists")
                .queryParam("categoryId", String.valueOf(categoryId))
                .queryParam("quantity", String.valueOf(quantity))
                .build().toUri();
        ResponseEntity<Campaign[]> response = restTemplate.exchange(uri, HttpMethod.GET, null, Campaign[].class);
        return Lists.newArrayList(response.getBody());
    }

}
