package com.trendyol.ecommerce.discount.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.trendyol.ecommerce.core.domain.Campaign;
import com.trendyol.ecommerce.core.domain.Category;
import com.trendyol.ecommerce.core.domain.DiscountType;
import com.trendyol.ecommerce.discount.model.request.CampaignCreateRequest;
import com.trendyol.ecommerce.discount.model.response.CampaignCreateResponse;
import com.trendyol.ecommerce.discount.service.CampaignService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CampaignController.class)
public class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignService campaignService;

    @Test
    public void createCampaignTest() throws Exception {

        Long categoryId = 1L;
        Integer threshold = 10;
        DiscountType discountType = DiscountType.RATE;
        BigDecimal discount = BigDecimal.valueOf(25);

        CampaignCreateRequest campaignCreateRequest = new CampaignCreateRequest(categoryId, threshold, discountType, discount);

        CampaignCreateResponse campaignCreateResponse = CampaignCreateResponse.builder()
                .campaignId(1L)
                .build();

        when(campaignService.createCampaign(any(CampaignCreateRequest.class))).thenReturn(campaignCreateResponse);

        mockMvc.perform(post("/api/v1/campaigns")
                .content(new Gson().toJson(campaignCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.campaignId", is(1)));
    }

    @Test
    public void getcCampaignByIdTest() throws Exception {

        Long categoryId = 1L;
        Integer threshold = 10;
        DiscountType discountType = DiscountType.RATE;
        BigDecimal discount = BigDecimal.valueOf(25);

        Campaign campaign = Campaign.builder()
                .id(1L)
                .threshold(threshold)
                .discountType(discountType)
                .discount(discount)
                .categoryId(categoryId)
                .build();
        when(campaignService.getCampaignById(any(Long.class))).thenReturn(campaign);

        mockMvc.perform(get("/api/v1/campaigns/{campaignId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(campaign)));
    }


}
