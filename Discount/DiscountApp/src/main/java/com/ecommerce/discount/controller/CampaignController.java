package com.ecommerce.discount.controller;

import com.ecommerce.common.domain.*;
import com.ecommerce.discount.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@ApiController
@RequestMapping(path = "/api/v1/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CampaignCreateResponse createCampaign(@RequestBody CampaignCreateRequest campaignCreateRequest) {
        CampaignCreateResponse campaignCreateResponse = campaignService.createCampaign(campaignCreateRequest);
        setSelfAndRelLink(campaignCreateResponse);
        return campaignCreateResponse;
    }

    @GetMapping(value = "/{campaignId}")
    public Campaign getcCampaignById(@PathVariable Long campaignId) {
        return campaignService.getCampaignById(campaignId);
    }

    @GetMapping(value = "/exists")
    public List<Campaign> getCampaignsByCategoryIdAndQuantity(@RequestParam Long categoryId, @RequestParam Integer quantity) {
        return campaignService.getCampaignsByCategoryIdAndQuantity(categoryId, quantity);
    }

    @PostMapping(value = "/calculateDiscount")
    public BigDecimal calculateProductDiscountIfCampaignExist(@RequestBody BasketItemDiscountCalculatorModel basketItem) {
        return campaignService.calculateProductDiscountIfCampaignExists(basketItem);
    }

    private void setSelfAndRelLink(ResourceSupport resourceSupport) {
        if (resourceSupport instanceof CampaignCreateResponse) {
            CampaignCreateResponse response = (CampaignCreateResponse) resourceSupport;
            response.add(linkTo(methodOn(CampaignController.class).getcCampaignById(response.getCampaignId())).withSelfRel());
        }
    }
}
