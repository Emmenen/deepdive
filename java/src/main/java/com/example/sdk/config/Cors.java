package com.example.sdk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;

@Configuration
public class Cors extends WebMvcConfigurationSupport {

    @Bean
    public HttpMessageConverter<String> responseBodyStringConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        return converter;
    }

    @Primary
    @Bean("corsConfigurationSource")
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");//修改为添加而不是设置，* 最好改为实际的需要，我这是非生产配置，所以粗暴了一点
        configuration.addAllowedMethod("*");//修改为添加而不是设置
        configuration.addAllowedHeader("*");//这里很重要，起码需要允许 Access-Control-Allow-Origin
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /*
     * 这里主要为了解决跨域问题,所以重写addCorsMappings方法
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:9528")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    //解决跨域后swagger-ui无法访问问题。重新指定静态资源配置
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("doc.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}
