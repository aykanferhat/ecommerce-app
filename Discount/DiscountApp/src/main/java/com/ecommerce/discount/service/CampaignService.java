package com.ecommerce.discount.service;

import com.ecommerce.common.domain.*;

import java.math.BigDecimal;
import java.util.List;

public interface CampaignService {

    CampaignCreateResponse createCampaign(CampaignCreateRequest campaignCreateRequest);

    Campaign getCampaignById(Long campaignId);

    List<Campaign> getCampaignsByCategoryIdAndQuantity(Long categoryId, Integer quantity);

    BigDecimal calculateProductDiscountIfCampaignExists(BasketItemDiscountCalculatorModel basketItem);
}
