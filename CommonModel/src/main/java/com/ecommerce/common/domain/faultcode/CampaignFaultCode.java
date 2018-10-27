package com.ecommerce.common.domain.faultcode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CampaignFaultCode implements FaultCode{

    CAMPAIGN_NOT_FOUND("error.rest.campaign.not.found.message"),
    ;

    private final String code;

    @Override
    public String getCode() {
        return code;
    }
}
