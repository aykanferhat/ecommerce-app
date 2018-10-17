package com.ecommerce.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "ecommerce", name = "basket_item_campaign")
public class BasketItemCampaign {

    @EmbeddedId
    private BasketItemCampaignId basketItemCampaignId;

    @ManyToOne
    @MapsId("basketItemId")
    @JoinColumn(name = "basket_item_id")
    private BasketItemEntity basketItemEntity;

    @ManyToOne
    @MapsId("campaignId")
    @JoinColumn(name = "campaign_id")
    private CampaignEntity campaignEntity;
}
