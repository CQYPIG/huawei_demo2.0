package com.cz.huawei_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.TimeUnit;

//开启事务支持
@EnableTransactionManagement
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
//        RedisTemplate redisTemplate = run.getBean(RedisTemplate.class);
        RedisTemplate aaaa = run.getBean("aaaa",RedisTemplate.class);

//        System.out.println(redisTemplate.getClass());

    }

}
