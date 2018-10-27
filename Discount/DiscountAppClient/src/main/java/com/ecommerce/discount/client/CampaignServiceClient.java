package com.ecommerce.discount.client;

import com.ecommerce.common.domain.*;
import com.ecommerce.discount.client.configuration.UrlProperties;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.ecommerce.common.domain.faultcode.CampaignFaultCode.CAMPAIGN_NOT_FOUND;
import static com.ecommerce.common.domain.faultcode.DiscountFaultCode.DISCOUNT_EXCEPTION;


@Slf4j
@RequiredArgsConstructor
public class CampaignServiceClient {

    private final RestTemplate restTemplate;

    public CampaignCreateResponse createCampaign(CampaignCreateRequest campaignCreateRequest) {
        try {
            HttpEntity<CampaignCreateRequest> httpEntity = new HttpEntity<>(campaignCreateRequest);
            ResponseEntity<CampaignCreateResponse> response = restTemplate.exchange(UrlProperties.Campaign.CREATE_CAMPAIGN_URL, HttpMethod.POST, httpEntity, CampaignCreateResponse.class);
            return response.getBody();
        } catch (Exception e) {
            throw new BusinessException(DISCOUNT_EXCEPTION);
        }
    }

    public Campaign getCampaignById(Long campaignId) {
        try {
            Map<String, String> uriParams = Maps.newHashMap();
            uriParams.put("campaignId", String.valueOf(campaignId));
            ResponseEntity<Campaign> response = restTemplate.exchange(UrlProperties.Campaign.GET_CAMPAIGN_BY_ID_URL, HttpMethod.GET, null, Campaign.class, uriParams);
            if (!response.hasBody()) {
                throw new BusinessException(CAMPAIGN_NOT_FOUND, campaignId);
            }
            return response.getBody();
        } catch (Exception e) {
            throw new BusinessException(DISCOUNT_EXCEPTION);
        }
    }

    public List<Campaign> getCampaignsByCategoryIdAndQuantity(Long categoryId, Integer quantity) {
        try {
            URI uri = UriComponentsBuilder.fromUriString(UrlProperties.Campaign.EXIST_CAMPAIGN_BY_FILTER_URL)
                    .queryParam("categoryId", String.valueOf(categoryId))
                    .queryParam("quantity", String.valueOf(quantity))
                    .build().toUri();
            ResponseEntity<Campaign[]> response = restTemplate.exchange(uri, HttpMethod.GET, null, Campaign[].class);
            return response.hasBody() ? Lists.newArrayList(response.getBody()) : Lists.newArrayList();
        } catch (Exception e) {
            throw new BusinessException(DISCOUNT_EXCEPTION);
        }
    }

    public BigDecimal calculateProductDiscountIfCampaignExists(BasketItemDiscountCalculatorModel basketItem) {
        try {
            HttpEntity<BasketItemDiscountCalculatorModel> httpEntity = new HttpEntity<>(basketItem);
            ResponseEntity<BigDecimal> response = restTemplate.exchange(UrlProperties.Campaign.CALCULATE_PRODUCT_DISCOUNT_IF_CAMPAIGN_EXISTS_URL, HttpMethod.POST, httpEntity, BigDecimal.class);
            return response.hasBody() ? response.getBody() : BigDecimal.ZERO;
        } catch (Exception e) {
            log.error("An error occurred when calculating Product Discount, BasketItem: {}", basketItem.getId());
            return BigDecimal.ZERO;
        }
    }

}
