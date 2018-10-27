package com.ecommerce.basket.service;

import com.ecommerce.common.domain.BasketCreateRequest;
import com.ecommerce.common.domain.BasketCreateResponse;
import com.ecommerce.basket.model.domain.Basket;

public interface BasketService {

    BasketCreateResponse createBasket(BasketCreateRequest basketCreateRequest);

    Basket getBasketById(Long basketId);
}
