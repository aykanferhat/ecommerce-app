package com.ecommerce.core.configuration;

import com.ecommerce.core.util.*;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

    @Bean
    public ExceptionMessageMapper exceptionMessageMapper(MessageSource messageSource) {
        return new ExceptionMessageMapper(messageSource);
    }
}
