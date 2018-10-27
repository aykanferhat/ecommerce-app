package com.ecommerce.discount.repository;

import com.ecommerce.core.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampaignRepository extends JpaRepository<CampaignEntity, Long> {

    @Query("select campaign from CampaignEntity campaign where campaign.categoryId = :categoryId and campaign.threshold <= :threshold")
    List<CampaignEntity> findByCategoryIdAndThreshold(@Param("categoryId") Long categoryId,
                                                      @Param("threshold") Integer threshold);
}
