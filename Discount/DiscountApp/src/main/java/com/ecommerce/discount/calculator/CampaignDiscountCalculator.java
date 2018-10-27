package com.ecommerce.discount.calculator;


import com.ecommerce.common.domain.Campaign;
import com.ecommerce.common.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CampaignDiscountCalculator {

    BigDecimal calculateDiscount(Product product, List<Campaign> campaigns);

}
