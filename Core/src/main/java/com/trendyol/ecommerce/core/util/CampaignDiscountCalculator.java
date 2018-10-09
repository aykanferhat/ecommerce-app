package com.trendyol.ecommerce.core.util;

import com.trendyol.ecommerce.core.domain.Campaign;
import com.trendyol.ecommerce.core.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CampaignDiscountCalculator {

    BigDecimal calculateDiscount(Product product, List<Campaign> campaigns);

    BigDecimal calculateTotalDisocunt(Product product, Integer quantity, List<Campaign> campaigns);

}
