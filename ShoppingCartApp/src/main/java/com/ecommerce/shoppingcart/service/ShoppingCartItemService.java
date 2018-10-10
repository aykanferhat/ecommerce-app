package com.ecommerce.shoppingcart.service;

import com.ecommerce.shoppingcart.model.request.ShoppingCartItemCreateRequest;
import com.ecommerce.shoppingcart.model.response.ShoppingCartItemCreateResponse;
import com.ecommerce.core.domain.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartItemService {

    ShoppingCartItemCreateResponse createShoppingCartItem(Long shoppingCartId, ShoppingCartItemCreateRequest shoppingCartItemCreateRequest);

    List<ShoppingCartItem> getShoppingCartItemsByCartId(Long shoppingCartId);
}
