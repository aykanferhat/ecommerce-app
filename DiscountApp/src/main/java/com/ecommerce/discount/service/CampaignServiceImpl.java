package com.ecommerce.discount.service;

import com.google.common.collect.Maps;
import com.ecommerce.core.domain.Campaign;
import com.ecommerce.core.entity.CampaignEntity;
import com.ecommerce.core.exception.BusinessException;
import com.ecommerce.discount.mapper.CampaignMapper;
import com.ecommerce.discount.model.request.CampaignCreateRequest;
import com.ecommerce.discount.model.response.CampaignCreateResponse;
import com.ecommerce.discount.repository.CampaignRepository;
import lombok.Builder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.ecommerce.core.exception.faultCode.CampaignFaultCode.CAMPAIGN_NOT_FOUND;
import static com.ecommerce.core.exception.faultCode.CategoryFaultCode.CATEGORY_NOT_FOUND;

@Builder
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
