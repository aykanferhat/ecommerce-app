package com.ecommerce.common.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Builder
public class BasketItemCreateResponse extends ResourceSupport {

    private Long basketItemId;
    private Long basketId;
}
