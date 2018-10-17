package com.ecommerce.basket.service;

import com.ecommerce.basket.model.request.BasketItemCreateRequest;
import com.ecommerce.basket.model.response.BasketItemCreateResponse;
import com.ecommerce.core.domain.BasketItem;

import java.util.List;

public interface BasketItemService {

    BasketItemCreateResponse createBasketItem(Long basketId, BasketItemCreateRequest basketItemCreateRequest);

    List<BasketItem> getBasketItemsByCartId(Long basketId);
}
