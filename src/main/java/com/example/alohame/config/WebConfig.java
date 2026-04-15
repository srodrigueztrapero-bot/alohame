package com.example.alohame.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.images.dir:${user.home}/alohame/images}")
    private String imagesDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String normalized = imagesDir.replace("\\", "/");
        if (!normalized.endsWith("/")) {
            normalized += "/";
        }

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + normalized, "classpath:/static/images/");
    }
}

