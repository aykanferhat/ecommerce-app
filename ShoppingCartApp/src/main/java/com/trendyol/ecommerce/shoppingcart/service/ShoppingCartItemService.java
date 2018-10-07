package com.trendyol.ecommerce.shoppingcart.service;

import com.trendyol.ecommerce.core.domain.ShoppingCartItem;
import com.trendyol.ecommerce.shoppingcart.model.request.ShoppingCartItemCreateRequest;
import com.trendyol.ecommerce.shoppingcart.model.response.ShoppingCartItemCreateResponse;

import java.util.List;

public interface ShoppingCartItemService {

    ShoppingCartItemCreateResponse createShoppingCartItem(Long shoppingCartId, ShoppingCartItemCreateRequest shoppingCartItemCreateRequest);

    List<ShoppingCartItem> getShoppingCartItemsByCartId(Long shoppingCartId);
}
