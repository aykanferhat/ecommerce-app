package com.ecommerce.shoppingcart.model.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

@Getter
@Builder
public class ShoppingCartCreateResponse extends ResourceSupport {

    private Long shoppingCartId;
    private Date creationDate;
    private Long userId;
}
