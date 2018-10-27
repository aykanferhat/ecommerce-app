package com.ecommerce.common.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {

    private Long categoryId;
    private Long parentCategoryId;
    private String title;
}
