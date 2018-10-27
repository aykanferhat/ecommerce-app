package com.ecommerce.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateRequest {

    private String title;
    private Long parentCategoryId;
}
