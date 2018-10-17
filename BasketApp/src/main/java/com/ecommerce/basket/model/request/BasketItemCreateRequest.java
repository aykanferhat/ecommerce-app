package com.ecommerce.basket.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemCreateRequest {

    private Integer quantity;
    private Long basketId;
    private Long basketItemId;
    private Long productId;
}
