package com.trendyol.ecommerce.product.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = "com.trendyol.ecommerce.product.repository")
@EntityScan( basePackages = "com.trendyol.ecommerce.core.entity")
public class PersistenceConfiguration {

}
