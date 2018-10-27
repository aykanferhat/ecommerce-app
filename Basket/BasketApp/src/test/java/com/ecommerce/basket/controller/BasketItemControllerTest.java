package com.ecommerce.basket.controller;

import com.ecommerce.basket.controller.error.ErrorController;
import com.ecommerce.common.domain.BasketItemCreateRequest;
import com.ecommerce.common.domain.BasketItemCreateResponse;
import com.google.gson.Gson;
import com.ecommerce.basket.service.BasketItemService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BasketItemController.class)
public class BasketItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketItemService basketItemService;

    @MockBean
    private ErrorController errorController;

    @Test
    public void createBasketTest() throws Exception {


        BigDecimal price = BigDecimal.valueOf(100);
        Integer quantity = 2;
        Long productId = 3L;

        Long basketItemId = 1L;
        Long basketId = 2L;

        BasketItemCreateRequest basketItemCreateRequest = new BasketItemCreateRequest(quantity, basketId,1L, productId);

        BasketItemCreateResponse basketItemCreateResponse = BasketItemCreateResponse.builder()
                .basketItemId(basketItemId)
                .basketId(basketId)
                .build();

        when(basketItemService.createBasketItem(any(Long.class), any(BasketItemCreateRequest.class))).thenReturn(basketItemCreateResponse);

        mockMvc.perform(post("/api/v1/baskets/{basketId}", basketId)
                .content(new Gson().toJson(basketItemCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.basketId", is(2)));
    }



}
