package com.trendyol.ecommerce.shoppingcart.service;

import com.google.common.collect.Maps;
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
import lombok.RequiredArgsConstructor;
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

import static com.trendyol.ecommerce.core.exception.faultCode.ProductFaultCode.PRODUCT_NOT_FOUND;
import static com.trendyol.ecommerce.core.exception.faultCode.ShoppingCartFaultCode.SHOPPING_CART_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final ShoppingCartItemMapper shoppingCartItemMapper;
    private final CampaignDiscountCalculator campaignDiscountCalculator;
    private final ShoppingCartItemCalculator shoppingCartItemCalculator;
    private final RestTemplate restTemplate;

    @Override
    public ShoppingCartItemCreateResponse createShoppingCartItem(Long shoppingCartId, ShoppingCartItemCreateRequest shoppingCartItemCreateRequest) {

        boolean existsShoppingCart = shoppingCartRepository.existsById(shoppingCartId);
        if (!existsShoppingCart) {
            throw new BusinessException(SHOPPING_CART_NOT_FOUND, shoppingCartId);
        }
        shoppingCartItemCreateRequest.setShoppingCartId(shoppingCartId);

        Long productId = shoppingCartItemCreateRequest.getProductId();
        Integer quantity = shoppingCartItemCreateRequest.getQuantity();

        ShoppingCartItemEntity shoppingCartItemEntity;
        Optional<ShoppingCartItemEntity> optionalShoppingCartItemEntity = shoppingCartItemRepository.findByShoppingCartIdAndProductId(shoppingCartId, productId);
        if (optionalShoppingCartItemEntity.isPresent()) {
            shoppingCartItemEntity = optionalShoppingCartItemEntity.get();
            quantity += shoppingCartItemEntity.getQuantity();
            shoppingCartItemEntity.setQuantity(quantity);
        } else {
            shoppingCartItemEntity = shoppingCartItemMapper.mapToEntity(shoppingCartItemCreateRequest);
        }
        Product product = getProductById(productId).orElseThrow(() -> new BusinessException(PRODUCT_NOT_FOUND, productId));

        BigDecimal cartItemTotalPrice = shoppingCartItemCalculator.calculateShoppingCartItemTotalPrice(product.getPrice(), quantity);
        shoppingCartItemEntity.setPrice(cartItemTotalPrice);

        List<Campaign> campaigns = getCampaignsByCategoryIdAndQuantity(product.getCategoryId(), shoppingCartItemCreateRequest.getQuantity());
        if (!campaigns.isEmpty()) {
            campaignDiscountCalculator.applyCampaignDiscount(shoppingCartItemEntity, product, campaigns);
        } else {
            shoppingCartItemEntity.setSalePrice(shoppingCartItemEntity.getPrice());
            shoppingCartItemEntity.setDiscount(BigDecimal.ZERO);
        }
        ShoppingCartItemEntity createdItem = shoppingCartItemRepository.save(shoppingCartItemEntity);
        return shoppingCartItemMapper.mapToCreateResponse(createdItem);
    }

    @Override
    public List<ShoppingCartItem> getShoppingCartItemsByCartId(Long shoppingCartId) {
        List<ShoppingCartItemEntity> shoppingCartItemEntities = shoppingCartItemRepository.findByShoppingCartId(shoppingCartId);
        if (shoppingCartItemEntities.isEmpty()) {
            return Lists.newArrayList();
        }
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemMapper.mapToDTOs(shoppingCartItemEntities);
        for (ShoppingCartItem shoppingCartItem: shoppingCartItems) {
            List<Campaign> campaigns = getCampaignsByCategoryIdAndQuantity(shoppingCartItem.getProduct().getCategoryId(), shoppingCartItem.getQuantity());
            if (!campaigns.isEmpty()) {
                shoppingCartItem.setCampaigns(campaigns);
                campaignDiscountCalculator.applyCampaignDiscount(shoppingCartItem);
            }
        }
        return shoppingCartItems;
    }

    private Optional<Product> getProductById(Long productId) {
        try {
            Map<String, String> uriParams = Maps.newHashMap();
            uriParams.put("productId", String.valueOf(productId));
            ResponseEntity<Product> responseEntity = restTemplate.exchange("http://product/api/v1/products/{productId}", HttpMethod.GET, null, Product.class, uriParams);
            return Optional.of(responseEntity.getBody());
        } catch (Exception exception) {
            throw new BusinessException(PRODUCT_NOT_FOUND, productId);
        }
    }

    private List<Campaign> getCampaignsByCategoryIdAndQuantity(Long categoryId, Integer quantity) {
        URI uri = UriComponentsBuilder.fromUriString("http://discount/api/v1/campaigns/exists")
                .queryParam("categoryId", String.valueOf(categoryId))
                .queryParam("quantity", String.valueOf(quantity))
                .build()
                .toUri();
        ResponseEntity<Campaign[]> response = restTemplate.exchange(uri, HttpMethod.GET, null, Campaign[].class);
        return Lists.newArrayList(response.getBody());
    }

}
