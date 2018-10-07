package com.trendyol.ecommerce.discount.service;

import com.trendyol.ecommerce.core.domain.Campaign;
import com.trendyol.ecommerce.discount.model.request.CampaignCreateRequest;
import com.trendyol.ecommerce.discount.model.response.CampaignCreateResponse;

import java.util.List;

public interface CampaignService {

    CampaignCreateResponse createCampaign(CampaignCreateRequest campaignCreateRequest);

    Campaign getCampaignById(Long campaignId);

    List<Campaign> getCampaignsByCategoryIdAndQuantity(Long categoryId, Integer quantity);
}
