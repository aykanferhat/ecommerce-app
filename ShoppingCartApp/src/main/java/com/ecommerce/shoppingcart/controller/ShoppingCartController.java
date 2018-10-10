package com.ecommerce.shoppingcart.controller;

import com.ecommerce.shoppingcart.model.request.ShoppingCartCreateRequest;
import com.ecommerce.core.domain.ShoppingCart;
import com.ecommerce.shoppingcart.model.response.ShoppingCartCreateResponse;
import com.ecommerce.shoppingcart.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@ApiController
@RequestMapping(path = "/api/v1/shoppingCarts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartCreateResponse createShoppingCart(@RequestBody ShoppingCartCreateRequest shoppingCartCreateRequest) {
        ShoppingCartCreateResponse shoppingCartCreateResponse = shoppingCartService.createShoppingCart(shoppingCartCreateRequest);
        setSelfAndRelLink(shoppingCartCreateResponse);
        return shoppingCartCreateResponse;
    }

    @GetMapping(value = "/{shoppingCartId}")
    public ShoppingCart getShoppingCartById(@PathVariable Long shoppingCartId) {
        return shoppingCartService.getShoppingCartById(shoppingCartId);
    }

    private void setSelfAndRelLink(ResourceSupport resourceSupport) {
        if (resourceSupport instanceof ShoppingCartCreateResponse) {
            ShoppingCartCreateResponse response = (ShoppingCartCreateResponse) resourceSupport;
            response.add(linkTo(methodOn(ShoppingCartController.class).getShoppingCartById(response.getShoppingCartId())).withSelfRel());
        }
    }
}
