package com.projectSpring.config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfigure implements WebMvcConfigurer {
//confguracion de vistas
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
      registry.addViewController("/403").setViewName("403");
    }
}
