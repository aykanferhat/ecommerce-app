package com.ecommerce.discount.mapper;

import com.ecommerce.common.domain.Campaign;
import com.ecommerce.common.domain.CampaignCreateRequest;
import com.ecommerce.common.domain.DiscountType;
import com.ecommerce.core.entity.CampaignEntity;
import com.ecommerce.common.domain.CampaignCreateResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CampaignMapper {

    public CampaignEntity mapToEntity(CampaignCreateRequest campaignCreateRequest) {

        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setCategoryId(campaignCreateRequest.getCategoryId());
        campaignEntity.setDiscount(campaignCreateRequest.getDiscount());
        campaignEntity.setDiscountType(campaignCreateRequest.getDiscountType().getValue());
        campaignEntity.setThreshold(campaignCreateRequest.getThreshold());
        return campaignEntity;
    }

    public CampaignCreateResponse mapToResponse(CampaignEntity campaignEntity) {

        return CampaignCreateResponse.builder()
                .campaignId(campaignEntity.getId())
                .build();
    }

    public Campaign mapToDTO(CampaignEntity campaignEntity) {

        return Campaign.builder()
                .id(campaignEntity.getId())
                .categoryId(campaignEntity.getCategoryId())
                .discount(campaignEntity.getDiscount())
                .discountType(DiscountType.valueOf(campaignEntity.getDiscountType()))
                .threshold(campaignEntity.getThreshold())
                .build();
    }

    public List<Campaign> mapToDTOs(List<CampaignEntity> campaignEntities) {

        return campaignEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
