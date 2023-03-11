package com.cz.huawei_demo.redis.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.Jedis;

@Configuration
@PropertySource(value = {"classpath:redis.properties"})
public class JedisConfig {


    @Value("${host}")
    private  String host;

    @Bean
    public Jedis jedis(){
        Jedis jedis = new Jedis(host,6379);
        return jedis;
    }
}
