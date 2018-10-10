package com.ecommerce.core.util;

import com.ecommerce.core.domain.Product;
import com.ecommerce.core.domain.Campaign;

import java.math.BigDecimal;
import java.util.List;

public interface CampaignDiscountCalculator {

    BigDecimal calculateDiscount(Product product, List<Campaign> campaigns);

    BigDecimal calculateTotalDisocunt(Product product, Integer quantity, List<Campaign> campaigns);

}
