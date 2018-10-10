package com.ecommerce.core.exception.faultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CampaignFaultCode implements BusinessFaultCode {

    CAMPAIGN_NOT_FOUND("error.rest.campaign.not.found.message"),
    ;

    private final String code;
}
