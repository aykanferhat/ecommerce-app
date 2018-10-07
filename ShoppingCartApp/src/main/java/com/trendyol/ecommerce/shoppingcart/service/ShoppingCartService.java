package com.trendyol.ecommerce.shoppingcart.service;

import com.trendyol.ecommerce.core.domain.ShoppingCart;
import com.trendyol.ecommerce.shoppingcart.model.request.ShoppingCartCreateRequest;
import com.trendyol.ecommerce.shoppingcart.model.response.ShoppingCartCreateResponse;

public interface ShoppingCartService {

    ShoppingCartCreateResponse createShoppingCart(ShoppingCartCreateRequest shoppingCartCreateRequest);

    ShoppingCart getShoppingCartById(Long shoppingCartId);
}
