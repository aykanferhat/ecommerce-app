package com.ecommerce.basket.controller;

import com.ecommerce.common.domain.BasketCreateRequest;
import com.ecommerce.common.domain.BasketCreateResponse;
import com.google.gson.Gson;
import com.ecommerce.basket.model.domain.Basket;
import com.ecommerce.basket.controller.error.ErrorController;
import com.ecommerce.basket.service.BasketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BasketController.class)
public class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    @MockBean
    private ErrorController errorController;

    @Test
    public void createBasketTest() throws Exception{

        Long userId = 1L;
        BasketCreateRequest basketCreateRequest = new BasketCreateRequest(userId);

        BasketCreateResponse basketCreateResponse = BasketCreateResponse.builder()
                .userId(userId)
                .basketId(2L)
                .build();

        when(basketService.createBasket(any(BasketCreateRequest.class))).thenReturn(basketCreateResponse);

        mockMvc.perform(post("/api/v1/baskets")
                .content(new Gson().toJson(basketCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.basketId", is(2)));
    }

    @Test
    public void getBasketByIdTest() throws Exception{

        Long basketId = 1L;

        Basket basket = Basket.builder()
                .id(1L)
                .build();

        when(basketService.getBasketById(any(Long.class))).thenReturn(basket);

        mockMvc.perform(get("/api/v1/baskets/{basketId}", basketId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)));
    }



}
