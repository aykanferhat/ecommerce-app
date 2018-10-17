package com.ecommerce.basket.service;

import com.ecommerce.basket.model.request.BasketCreateRequest;
import com.ecommerce.basket.model.response.BasketCreateResponse;
import com.ecommerce.core.domain.Basket;

public interface BasketService {

    BasketCreateResponse createBasket(BasketCreateRequest basketCreateRequest);

    Basket getBasketById(Long basketId);
}
