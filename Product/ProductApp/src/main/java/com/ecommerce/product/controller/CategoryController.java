package com.ecommerce.product.controller;

import com.ecommerce.common.domain.Category;
import com.ecommerce.common.domain.CategoryCreateRequest;
import com.ecommerce.common.domain.CategoryCreateResponse;
import com.ecommerce.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@ApiController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryCreateResponse createCategory(@RequestBody CategoryCreateRequest categoryCreateRequest) {
        CategoryCreateResponse response = categoryService.createCategory(categoryCreateRequest);
        setSelfAndRelLink(response);
        return response;
    }

    @GetMapping(value = "/{categoryId}")
    public Category getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping(value = "/{categoryId}/exists")
    @ResponseBody
    public Boolean existsCategory(@PathVariable Long categoryId) {
        return categoryService.existsById(categoryId);
    }


    private void setSelfAndRelLink(ResourceSupport resourceSupport) {
        if (resourceSupport instanceof CategoryCreateResponse) {
            CategoryCreateResponse response = (CategoryCreateResponse) resourceSupport;
            resourceSupport.add(linkTo(methodOn(CategoryController.class).getCategoryById(response.getCategoryId())).withSelfRel());
            if (response.getParentCategoryId() != null) {
                resourceSupport.add(linkTo(methodOn(CategoryController.class).getCategoryById(response.getParentCategoryId())).withRel("parentCategory"));
            }
        }
    }
}
