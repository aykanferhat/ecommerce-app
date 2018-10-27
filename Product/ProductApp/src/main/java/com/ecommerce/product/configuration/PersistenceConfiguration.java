package com.ecommerce.product.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = "com.ecommerce.product.repository")
@EntityScan( basePackages = "com.ecommerce.core.entity")
public class PersistenceConfiguration {

}
