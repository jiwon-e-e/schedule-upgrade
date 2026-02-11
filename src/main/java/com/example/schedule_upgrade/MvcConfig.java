package com.example.schedule_upgrade;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;
    private final LoginUserArgumentResolver loginuserArgumentResolver;

    public MvcConfig(LoginCheckInterceptor loginCheckInterceptor,
                     LoginUserArgumentResolver loginUserArgumentResolver) {
        this.loginCheckInterceptor = loginCheckInterceptor;
        this.loginuserArgumentResolver = loginUserArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/signup")
                .excludeHttpMethods(HttpMethod.GET);
    }
}

