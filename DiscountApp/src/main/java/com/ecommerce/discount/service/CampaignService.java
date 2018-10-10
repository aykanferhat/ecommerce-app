package com.ecommerce.discount.service;

import com.ecommerce.core.domain.Campaign;
import com.ecommerce.discount.model.request.CampaignCreateRequest;
import com.ecommerce.discount.model.response.CampaignCreateResponse;

import java.util.List;

public interface CampaignService {

    CampaignCreateResponse createCampaign(CampaignCreateRequest campaignCreateRequest);

    Campaign getCampaignById(Long campaignId);

    List<Campaign> getCampaignsByCategoryIdAndQuantity(Long categoryId, Integer quantity);
}
