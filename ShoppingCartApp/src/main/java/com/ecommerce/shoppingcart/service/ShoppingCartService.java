package com.ecommerce.shoppingcart.service;

import com.ecommerce.shoppingcart.model.request.ShoppingCartCreateRequest;
import com.ecommerce.shoppingcart.model.response.ShoppingCartCreateResponse;
import com.ecommerce.core.domain.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCartCreateResponse createShoppingCart(ShoppingCartCreateRequest shoppingCartCreateRequest);

    ShoppingCart getShoppingCartById(Long shoppingCartId);
}
