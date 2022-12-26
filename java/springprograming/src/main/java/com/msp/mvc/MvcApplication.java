package com.msp.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@EnableWebMvc
@Configuration
@SpringBootApplication
public class MvcApplication implements WebMvcConfigurer {

    private static ConfigurableApplicationContext ctx = null;

    public static void main(String[] args) {
        ctx = SpringApplication.run(MvcApplication.class, args);
    }

//    @Bean
//    public InternalResourceViewResolver configureViewResolver() {
//        InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
//        viewResolve.setPrefix("/WEB-INF/views/");
//        viewResolve.setSuffix(".jsp");
//
//        return viewResolve;
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Bean
    public SpringResourceTemplateResolver jspTemplateResolver() {
        final SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(ctx);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        templateResolver.setOrder(1);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

}
