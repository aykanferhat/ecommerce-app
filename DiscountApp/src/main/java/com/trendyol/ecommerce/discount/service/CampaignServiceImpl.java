package com.trendyol.ecommerce.discount.service;

import com.google.common.collect.Maps;
import com.trendyol.ecommerce.core.domain.Campaign;
import com.trendyol.ecommerce.core.entity.CampaignEntity;
import com.trendyol.ecommerce.core.exception.BusinessException;
import com.trendyol.ecommerce.discount.mapper.CampaignMapper;
import com.trendyol.ecommerce.discount.model.request.CampaignCreateRequest;
import com.trendyol.ecommerce.discount.model.response.CampaignCreateResponse;
import com.trendyol.ecommerce.discount.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.trendyol.ecommerce.core.exception.faultCode.CampaignFaultCode.CAMPAIGN_NOT_FOUND;
import static com.trendyol.ecommerce.core.exception.faultCode.CategoryFaultCode.CATEGORY_NOT_FOUND;

@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService{

    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;
    private final RestTemplate restTemplate;

    @Override
    public CampaignCreateResponse createCampaign(CampaignCreateRequest campaignCreateRequest) {
        Long categoryId = campaignCreateRequest.getCategoryId();
        if (!existsCategory(categoryId)) {
            throw new BusinessException(CATEGORY_NOT_FOUND, categoryId);
        }
        CampaignEntity campaignEntity = campaignRepository.save(campaignMapper.mapToEntity(campaignCreateRequest));
        return campaignMapper.mapToResponse(campaignEntity);
    }

    @Override
    public Campaign getCampaignById(Long campaignId) {
        Optional<CampaignEntity> optionalCampaignEntity = campaignRepository.findById(campaignId);
        if (!optionalCampaignEntity.isPresent()) {
            throw new BusinessException(CAMPAIGN_NOT_FOUND, campaignId);
        }
        CampaignEntity campaignEntity = optionalCampaignEntity.get();
        return campaignMapper.mapToDTO(campaignEntity);
    }

    @Override
    public List<Campaign> getCampaignsByCategoryIdAndQuantity(Long categoryId, Integer quantity) {
        List<CampaignEntity> campaignEntities = campaignRepository.findByCategoryIdAndThreshold(categoryId, quantity);
        return campaignMapper.mapToDTOs(campaignEntities);
    }

    private boolean existsCategory(Long categoryId) {
        Map<String, String> uriParams = Maps.newHashMap();
        uriParams.put("categoryId", String.valueOf(categoryId));
        ResponseEntity<Boolean> response = restTemplate.exchange("http://product/api/v1/categories/{categoryId}/exists", HttpMethod.GET, null, Boolean.class, uriParams);
        return response.getBody();
    }
}
