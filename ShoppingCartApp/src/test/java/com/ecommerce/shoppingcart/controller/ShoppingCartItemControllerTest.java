package com.ecommerce.shoppingcart.controller;

import com.ecommerce.shoppingcart.controller.error.ErrorController;
import com.ecommerce.shoppingcart.model.request.ShoppingCartItemCreateRequest;
import com.ecommerce.shoppingcart.model.response.ShoppingCartItemCreateResponse;
import com.google.gson.Gson;
import com.ecommerce.shoppingcart.service.ShoppingCartItemService;
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
@WebMvcTest(ShoppingCartItemController.class)
public class ShoppingCartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartItemService shoppingCartItemService;

    @MockBean
    private ErrorController errorController;

    @Test
    public void createShoppingCartTest() throws Exception {


        BigDecimal price = BigDecimal.valueOf(100);
        Integer quantity = 2;
        Long productId = 3L;

        Long shoppingCartItemId = 1L;
        Long shoppingCartId = 2L;

        ShoppingCartItemCreateRequest shoppingCartItemCreateRequest = new ShoppingCartItemCreateRequest(quantity, shoppingCartId, productId);

        ShoppingCartItemCreateResponse shoppingCartItemCreateResponse = ShoppingCartItemCreateResponse.builder()
                .shoppingCartItemId(shoppingCartItemId)
                .shoppingCartId(shoppingCartId)
                .build();

        when(shoppingCartItemService.createShoppingCartItem(any(Long.class), any(ShoppingCartItemCreateRequest.class))).thenReturn(shoppingCartItemCreateResponse);

        mockMvc.perform(post("/api/v1/shoppingCarts/{shoppingCartId}", shoppingCartId)
                .content(new Gson().toJson(shoppingCartItemCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shoppingCartId", is(2)));
    }



}
