package org.example.numbeer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class ValidationConfiguration {

//    public   ValidationConfiguration() {
//    }
//    private    ValidationConfiguration() {
//    }

    @Bean
    public static MethodValidationPostProcessor validator() {
        return new MethodValidationPostProcessor();
    }

}

