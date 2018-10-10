package com.ecommerce.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ShoppingCart {

    private Long id;
    private Date creationDate;
    private Long userId;
    private BigDecimal totalPrice;
    private BigDecimal salePrice;
    private BigDecimal discount;
    private Integer totalQuantity;
    private List<ShoppingCartItem> shoppingCartItems;
    private Coupon coupon;
}
