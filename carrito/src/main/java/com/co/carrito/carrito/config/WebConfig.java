package com.co.carrito.carrito.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/FotoServicio/**")
                .addResourceLocations("file:C:/Users/USUARIO/Downloads/carrito (3)/carrito/public/images/");
    }
}