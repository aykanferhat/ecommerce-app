package com.ecommerce.shoppingcart.model.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Builder
public class ShoppingCartItemCreateResponse extends ResourceSupport {

    private Long shoppingCartItemId;
    private Long shoppingCartId;
}
