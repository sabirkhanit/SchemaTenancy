package com.schema.tenancy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.schema.tenancy.web.interceptor.RequestInterceptor;

/**
 * @author Sabir Khan
 *
 */
@Configuration
@EnableTransactionManagement
public class SchemaTenancyAppConfiguration implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(SchemaTenancyAppConfiguration.class);

    @Value("${cors.allowed.origin:*}")
    private String origin;

    @Autowired
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(requestInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
	logger.info("Cors allowed origin {}", origin);
	registry.addMapping("/**").allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE", "PATCH")
		.allowedOrigins(origin).allowedHeaders("*");
    }
}
