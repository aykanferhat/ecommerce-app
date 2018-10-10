package com.ecommerce.discount.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ecommerce.discount.repository")
@EntityScan( basePackages = {"com.ecommerce.core.entity"} )
public class PersistenceConfiguration {

}
