package com.trendyol.ecommerce.shoppingcart.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class DataSourceProperties {

    private String jdbcUrl;
    private String username;
    private String password;
    private String poolName;
    private int connectionTimeout;
    private int maximumPoolSize;
}

