package com.ecommerce.product.controller;

import com.ecommerce.product.model.request.ProductCreateRequest;
import com.ecommerce.product.model.response.ProductCreateResponse;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.core.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@ApiController
@RequestMapping(path = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCreateResponse createProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        ProductCreateResponse response = productService.createProduct(productCreateRequest);
        setSelfAndRelLink(response);
        return response;
    }

    @GetMapping(path = "/{productId}/exists")
    @ResponseBody
    public Boolean existsById(@PathVariable Long productId) {
        return productService.existsById(productId);
    }

    @GetMapping(path = "/{productId}")
    @ResponseBody
    public Product getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    private void setSelfAndRelLink(ResourceSupport resourceSupport) {
        if (resourceSupport instanceof ProductCreateResponse) {
            ProductCreateResponse response = (ProductCreateResponse) resourceSupport;
            response.add(linkTo(methodOn(CategoryController.class).getCategoryById(response.getCategoryId())).withRel("category"));
        }
    }
}
