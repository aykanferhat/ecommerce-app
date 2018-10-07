package com.trendyol.ecommerce.product.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {

    private String title;
    private BigDecimal price;
    private Long categoryId;

}
