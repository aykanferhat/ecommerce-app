package com.ecommerce.basket.model.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Builder
public class BasketItemCreateResponse extends ResourceSupport {

    private Long basketItemId;
    private Long basketId;
}
