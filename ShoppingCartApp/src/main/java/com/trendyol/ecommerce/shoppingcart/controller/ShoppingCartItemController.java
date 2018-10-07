package com.trendyol.ecommerce.shoppingcart.controller;

import com.trendyol.ecommerce.shoppingcart.model.request.ShoppingCartItemCreateRequest;
import com.trendyol.ecommerce.shoppingcart.model.response.ShoppingCartItemCreateResponse;
import com.trendyol.ecommerce.shoppingcart.service.ShoppingCartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@ApiController
@RequestMapping(path = "/api/v1/shoppingCarts/{shoppingCartId}")
@RequiredArgsConstructor
public class ShoppingCartItemController {

    private final ShoppingCartItemService shoppingCartItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartItemCreateResponse createShoppingCartItem(
            @PathVariable Long shoppingCartId,
            @RequestBody ShoppingCartItemCreateRequest shoppingCartItemCreateRequest) {
        ShoppingCartItemCreateResponse shoppingCartItemResponse = shoppingCartItemService.createShoppingCartItem(shoppingCartId, shoppingCartItemCreateRequest);
        setSelfAndRelLink(shoppingCartItemResponse);
        return shoppingCartItemResponse;
    }

    private void setSelfAndRelLink(ResourceSupport resourceSupport) {
        if (resourceSupport instanceof ShoppingCartItemCreateResponse) {
            ShoppingCartItemCreateResponse response = (ShoppingCartItemCreateResponse) resourceSupport;
            response.add(linkTo(methodOn(ShoppingCartController.class).getShoppingCartById(response.getShoppingCartId())).withRel("shoppingCart"));
        }
    }
}
