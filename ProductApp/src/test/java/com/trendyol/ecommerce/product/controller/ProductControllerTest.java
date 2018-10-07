package com.trendyol.ecommerce.product.controller;

import com.google.gson.Gson;
import com.trendyol.ecommerce.product.model.request.ProductCreateRequest;
import com.trendyol.ecommerce.product.model.response.ProductCreateResponse;
import com.trendyol.ecommerce.product.service.ProductService;
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
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void createProductTest() throws Exception{

        String title = "TestProduct";
        BigDecimal price = BigDecimal.valueOf(100);
        Long categoryId = 1L;

        ProductCreateRequest productCreateRequest = new ProductCreateRequest(title, price, categoryId);

        ProductCreateResponse productCreateResponse = ProductCreateResponse.builder()
                .productId(1L)
                .categoryId(2L)
                .build();

        when(productService.createProduct(any(ProductCreateRequest.class))).thenReturn(productCreateResponse);

        mockMvc.perform(post("/api/v1/products")
                .content(new Gson().toJson(productCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId", is(2)));
    }

    @Test
    public void existByIdTest() throws Exception {

        Long productId = 1L;

        when(productService.existsById(any(Long.class))).thenReturn(Boolean.TRUE);

        mockMvc.perform(get("/api/v1/products/{productId}/exists", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("true"));
    }
    
}
