package com.ecommerce.discount.model.request;

import com.ecommerce.core.domain.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignCreateRequest {

    private Long categoryId;
    private Integer threshold;
    private DiscountType discountType;
    private BigDecimal discount;
}
