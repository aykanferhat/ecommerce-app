package com.ecommerce.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
public class BasketItemCampaignId {

    private Long basketItemId;
    private Long campaignId;
}
