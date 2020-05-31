package com.buct.graduation.config;

import com.buct.graduation.config.interceptor.AdminInterceptor;
import com.buct.graduation.config.interceptor.TeacherInterceptor;
import com.buct.graduation.config.interceptor.UserInterceptor;
import com.buct.graduation.util.GlobalName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    private UserInterceptor loginInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;
    @Autowired
    private TeacherInterceptor teacherInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //linux 路径
        registry.addResourceHandler("/"+GlobalName.MAPPING_PATH+"/**").addResourceLocations("file:"+ GlobalName.ABSOLUTE_PATH);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns();
        registry.addInterceptor(adminInterceptor).addPathPatterns("/**").excludePathPatterns();
        registry.addInterceptor(teacherInterceptor).addPathPatterns("/**").excludePathPatterns();
    }
}
