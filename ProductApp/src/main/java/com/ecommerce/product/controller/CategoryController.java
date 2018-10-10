package com.ecommerce.product.controller;

import com.ecommerce.product.model.response.CategoryCreateResponse;
import com.ecommerce.product.service.CategoryService;
import com.ecommerce.core.domain.Category;
import com.ecommerce.product.model.request.CategoryCreateRequest;
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
