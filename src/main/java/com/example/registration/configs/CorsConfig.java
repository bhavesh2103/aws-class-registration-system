package com.example.registration.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Allow requests from any origin. You can specify a specific origin instead.
        config.addAllowedMethod("*"); // Allow all HTTP methods.
        config.addAllowedHeader("*"); // Allow all headers.
        source.registerCorsConfiguration("/**", config); // Apply this configuration to all URL paths.

        return new CorsFilter(source);
    }

}
