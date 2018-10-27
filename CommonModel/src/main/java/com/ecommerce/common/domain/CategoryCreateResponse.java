package com.ecommerce.common.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Builder
public class CategoryCreateResponse extends ResourceSupport {

    private Long categoryId;
    private Long parentCategoryId;
}
