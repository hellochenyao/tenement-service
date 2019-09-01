package com.katana.tenement.web.main.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.katana.tenement.web.main.interceptor.WxLoginJwtInterception;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/housingResource/**").addResourceLocations("/uploadResource/housingResource/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginJwtInterception())
                .addPathPatterns("/app/**")
                .excludePathPatterns("/app/*/auth/**");
    }

    @Bean
    public WxLoginJwtInterception loginJwtInterception() {
        return new WxLoginJwtInterception();
    }

    @Bean
    @ConditionalOnBean
    public ObjectMapper getObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        om.registerModule(javaTimeModule);
        return om;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 文件上传配置
     *
     * @return MultipartConfigElement
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(
            @Value("${spring.http.multipart.max-file-size}") String maxFileSize,
            @Value("${spring.http.multipart.max-request-size}") String maxRequestSize) {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize(maxFileSize);
        // 设置总上传数据总大小
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }
}
