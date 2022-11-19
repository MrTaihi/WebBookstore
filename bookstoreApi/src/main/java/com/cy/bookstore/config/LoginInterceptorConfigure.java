package com.cy.bookstore.config;

import com.cy.bookstore.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

//登录拦截器的注册
//@Configuration   // 加载拦截器并注册 这个是配置文件必须要写的
public class LoginInterceptorConfigure implements WebMvcConfigurer {
    //重写方法
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new LoginInterceptor();
        List<String> patterns = new ArrayList<>();
        patterns.add("/user/reg");   // 将白名单的目录写入
        patterns.add("/user/login");   // 将白名单的目录写入

//        registry.addInterceptor(interceptor)  先暂时不注册拦截器 因为这是个前后端分离的项目 可以先在后端做完 然后再在前端做 双重保险
//                .addPathPatterns("/**")       // 表示要拦截的url 是黑名单
//                .excludePathPatterns(patterns);  // 表示除了什么之外 是白名单
    }
}
