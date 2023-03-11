package com.cz.huawei_demo.config;


import com.cz.huawei_demo.servlet.LoginStateFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 将自定义的servlet组件注册进容器中
 * @author yingfeng
 * @date 2022/10/27 18:07
 */
@Configuration
public class ServletRegisterConfig {
    @Bean
    public FilterRegistrationBean loginStateFilter(){
        LoginStateFilter loginStateFilter = new LoginStateFilter();
        return new FilterRegistrationBean(loginStateFilter);
    }
}
