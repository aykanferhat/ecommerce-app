package com.trendyol.ecommerce.core.util;

import com.trendyol.ecommerce.core.domain.Campaign;
import com.trendyol.ecommerce.core.domain.DiscountType;
import com.trendyol.ecommerce.core.domain.Product;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class CampaignDiscountCalculatorImpl implements CampaignDiscountCalculator{

    @Override
    public BigDecimal calculateTotalDisocunt(Product product, Integer quantity, List<Campaign> campaigns) {
        return calculateDiscount(product, campaigns).multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public BigDecimal calculateDiscount(Product product, List<Campaign> campaigns) {
        BigDecimal dicount = BigDecimal.ZERO;
        for (Campaign campaign: campaigns) {
            BigDecimal discountAmount = BigDecimal.ZERO;
            DiscountType discountType = campaign.getDiscountType();
            if (discountType.isRate()) {
                discountAmount = product.getPrice().multiply(campaign.getDiscount()).divide(BigDecimal.valueOf(100));
            } else if (discountType.isAmount()) {
                discountAmount = campaign.getDiscount();
            }
            dicount = dicount.add(discountAmount);
        }
        return dicount;
    }
}
