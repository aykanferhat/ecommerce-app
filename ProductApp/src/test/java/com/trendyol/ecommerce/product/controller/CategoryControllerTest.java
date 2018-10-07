package com.trendyol.ecommerce.product.controller;

import com.google.gson.Gson;
import com.trendyol.ecommerce.core.domain.Category;
import com.trendyol.ecommerce.product.model.request.CategoryCreateRequest;
import com.trendyol.ecommerce.product.model.response.CategoryCreateResponse;
import com.trendyol.ecommerce.product.service.CategoryService;
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
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void createCategoryTest() throws Exception {

        String title = "TestCategory";
        Long parentCategoryId = 1L;
        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest(title, parentCategoryId);

        CategoryCreateResponse categoryCreateResponse = CategoryCreateResponse.builder()
                .parentCategoryId(1L)
                .categoryId(2L)
                .build();

        when(categoryService.createCategory(any(CategoryCreateRequest.class))).thenReturn(categoryCreateResponse);

        mockMvc.perform(post("/api/v1/categories")
                .content(new Gson().toJson(categoryCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId", is(2)));
    }

    @Test
    public void getCategoryByIdTest() throws Exception {

        Long categoryId = 1L;

        Category category = Category.builder()
                .title("TestCategory")
                .build();

        when(categoryService.getCategoryById(any(Long.class))).thenReturn(category);

        mockMvc.perform(get("/api/v1/categories/{categoryId}", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.title", is(category.getTitle())));
    }


    @Test
    public void existsCategoryTest() throws Exception {

        Long categoryId = 1L;

        when(categoryService.existsById(any(Long.class))).thenReturn(Boolean.TRUE);

        mockMvc.perform(get("/api/v1/categories/{categoryId}/exists", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("true"));
    }

}
