package com.ecommerce.basket.controller;

import com.ecommerce.basket.model.request.BasketCreateRequest;
import com.ecommerce.core.domain.Basket;
import com.ecommerce.basket.model.response.BasketCreateResponse;
import com.ecommerce.basket.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@ApiController
@RequestMapping(path = "/api/v1/baskets")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BasketCreateResponse createBasket(@RequestBody BasketCreateRequest basketCreateRequest) {
        BasketCreateResponse basketCreateResponse = basketService.createBasket(basketCreateRequest);
        setSelfAndRelLink(basketCreateResponse);
        return basketCreateResponse;
    }

    @GetMapping(value = "/{basketId}")
    public Basket getBasketById(@PathVariable Long basketId) {
        return basketService.getBasketById(basketId);
    }

    private void setSelfAndRelLink(ResourceSupport resourceSupport) {
        if (resourceSupport instanceof BasketCreateResponse) {
            BasketCreateResponse response = (BasketCreateResponse) resourceSupport;
            response.add(linkTo(methodOn(BasketController.class).getBasketById(response.getBasketId())).withSelfRel());
        }
    }
}
