package com.trendyol.ecommerce.discount.configuration;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceProperties.getJdbcUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setConnectionTimeout(dataSourceProperties.getConnectionTimeout());
        dataSource.setMaximumPoolSize(dataSourceProperties.getMaximumPoolSize());
        dataSource.setPoolName(dataSourceProperties.getPoolName());
        return dataSource;
    }
}
