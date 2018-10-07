package com.trendyol.ecommerce.discount.model.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
@Builder
public class CampaignCreateResponse extends ResourceSupport {

    private Long campaignId;
}
