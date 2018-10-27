package com.ecommerce.basket.controller;

import com.ecommerce.common.domain.BasketItemCreateResponse;
import com.ecommerce.common.domain.BasketItemCreateRequest;
import com.ecommerce.basket.service.BasketItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@ApiController
@RequestMapping(path = "/api/v1/baskets/{basketId}")
@RequiredArgsConstructor
public class BasketItemController {

    private final BasketItemService basketItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BasketItemCreateResponse createBasketItem(
            @PathVariable Long basketId,
            @RequestBody BasketItemCreateRequest basketItemCreateRequest) {
        BasketItemCreateResponse basketItemCreateResponse = basketItemService.createBasketItem(basketId, basketItemCreateRequest);
        setSelfAndRelLink(basketItemCreateResponse);
        return basketItemCreateResponse;
    }

    private void setSelfAndRelLink(ResourceSupport resourceSupport) {
        if (resourceSupport instanceof BasketItemCreateResponse) {
            BasketItemCreateResponse response = (BasketItemCreateResponse) resourceSupport;
            response.add(linkTo(methodOn(BasketController.class).getBasketById(response.getBasketId())).withRel("basket"));
        }
    }
}
