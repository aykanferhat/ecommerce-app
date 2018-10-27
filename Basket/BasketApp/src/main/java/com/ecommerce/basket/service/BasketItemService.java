package com.ecommerce.basket.service;

import com.ecommerce.common.domain.BasketItem;
import com.ecommerce.common.domain.BasketItemCreateRequest;
import com.ecommerce.common.domain.BasketItemCreateResponse;

import java.util.List;

public interface BasketItemService {

    BasketItemCreateResponse createBasketItem(Long basketId, BasketItemCreateRequest basketItemCreateRequest);

    List<BasketItem> getBasketItemsByCartId(Long basketId);
}
