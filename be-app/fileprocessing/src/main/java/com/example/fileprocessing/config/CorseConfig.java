package com.example.fileprocessing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CorseConfig implements WebMvcConfigurer {
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("*") // Replace with the origin of your React application
	                .allowedMethods("GET", "POST", "PUT", "DELETE")
	                .allowedHeaders("*");
	    }
}
