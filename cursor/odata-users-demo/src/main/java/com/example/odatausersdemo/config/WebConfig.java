package com.example.odatausersdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);
    
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Disable trailing slash redirects
        logger.info("Configuring path matching to disable trailing slash redirects");
        configurer.setUseTrailingSlashMatch(false);
    }
} 