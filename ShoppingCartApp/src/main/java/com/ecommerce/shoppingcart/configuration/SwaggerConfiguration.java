package com.ecommerce.shoppingcart.configuration;

import com.google.common.base.Predicate;
import com.ecommerce.shoppingcart.controller.ApiController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket shoppingCartApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(ApiController.class))
                .paths(paths())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Trendyol E-Commerce Shopping Cart Api")
                .description("Trendyol E-Commerce Shopping Cart Api Docs")
                .version("v1")
                .build();
    }

    private Predicate<String> paths(){
        return or(
                regex(".*products.*"),
                regex(".*categories.*")
        );
    }
}
