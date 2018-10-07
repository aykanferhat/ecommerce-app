package com.trendyol.ecommerce.product.model.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Builder
public class ProductCreateResponse extends ResourceSupport {

    private Long productId;
    private Long categoryId;
}
