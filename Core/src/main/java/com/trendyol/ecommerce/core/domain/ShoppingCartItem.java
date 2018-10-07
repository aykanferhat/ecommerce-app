package com.trendyol.ecommerce.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Builder
public class ShoppingCartItem {

    private Long id;
    private BigDecimal salePrice;
    private BigDecimal discount;
    private BigDecimal price;
    private Integer quantity;
    private Product product;
    private List<Campaign> campaigns;
}
