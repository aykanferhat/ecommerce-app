package com.trendyol.ecommerce.shoppingcart.controller;

import com.google.gson.Gson;
import com.trendyol.ecommerce.core.domain.ShoppingCart;
import com.trendyol.ecommerce.shoppingcart.model.request.ShoppingCartCreateRequest;
import com.trendyol.ecommerce.shoppingcart.model.response.ShoppingCartCreateResponse;
import com.trendyol.ecommerce.shoppingcart.service.ShoppingCartService;
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
@WebMvcTest(ShoppingCartController.class)
public class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @Test
    public void createShoppingCartTest() throws Exception{

        Long userId = 1L;
        ShoppingCartCreateRequest shoppingCartCreateRequest = new ShoppingCartCreateRequest(userId);

        ShoppingCartCreateResponse shoppingCartCreateResponse = ShoppingCartCreateResponse.builder()
                .userId(userId)
                .shoppingCartId(2L)
                .build();

        when(shoppingCartService.createShoppingCart(any(ShoppingCartCreateRequest.class))).thenReturn(shoppingCartCreateResponse);

        mockMvc.perform(post("/api/v1/shoppingCarts")
                .content(new Gson().toJson(shoppingCartCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shoppingCartId", is(2)));
    }

    @Test
    public void getShoppingCartByIdTest() throws Exception{

        Long shoppingCartId = 1L;

        ShoppingCart shoppingCart = ShoppingCart.builder()
                .id(1L)
                .build();

        when(shoppingCartService.getShoppingCartById(any(Long.class))).thenReturn(shoppingCart);

        mockMvc.perform(get("/api/v1/shoppingCarts/{shoppingCartId}", shoppingCartId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)));
    }



}
