package com.khylo.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${app.api.cors.allowedOrigins}")
  private String allowedOrigins;

  @Value("${app.api.cors.allowedMethods}")
  private String allowedMethods;

  @Value("${app.api.cors.allowedHeaders}")
  private String allowedHeaders;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**") // Apply to all endpoints under /api/
      .allowedOrigins("*") // Allow this origin
      .allowedMethods("*") // Allow these methods (customize as needed)
      .allowedHeaders("*"); // Allow all headers (or specify allowed headers)
  }
}
