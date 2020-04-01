package com.ncut.face.erp.bootstrap.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class PropertiesConfig {
    @Configuration
    @Profile("dev")
    @PropertySource("classpath:dev.properties")
    static class Development {
    }

    @Configuration
    @Profile("prod")
    @PropertySource("classpath:prod.properties")
    static class Product {
    }

}
