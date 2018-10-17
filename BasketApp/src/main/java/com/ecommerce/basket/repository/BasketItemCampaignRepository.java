package com.ecommerce.basket.repository;

import com.ecommerce.core.entity.BasketItemCampaign;
import com.ecommerce.core.entity.BasketItemCampaignId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketItemCampaignRepository extends JpaRepository<BasketItemCampaign, BasketItemCampaignId> {

    List<BasketItemCampaign> findByBasketItemCampaignIdBasketItemId(Long basketItemId);

    List<BasketItemCampaign> findByBasketItemCampaignIdCampaignId(Long campaignId);

    BasketItemCampaign findByBasketItemCampaignIdBasketItemIdAndBasketItemCampaignIdCampaignId(Long basketId, Long campaignId);
}
