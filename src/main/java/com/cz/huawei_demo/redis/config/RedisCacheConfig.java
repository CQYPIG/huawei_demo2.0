package com.cz.huawei_demo.redis.config;
import com.cz.huawei_demo.Application;
import com.cz.huawei_demo.until.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.locks.ReadWriteLock;

@Slf4j

public class RedisCacheConfig implements Cache {

    private final String id;

    public RedisCacheConfig(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        //使用redis中的hash类型作为缓存的存储类型
        getRedisTemplate().opsForHash().put(id.toString(), key.toString(), value);
        log.info("查询结果存入缓存：id:{} key:{} value:{}", id.toString(), key.toString(), value.toString());
    }

    @Override
    public Object getObject(Object key) {
        //获取存储的数据
        Object value = getRedisTemplate().opsForHash().get(id.toString(), key.toString());
        log.info("取到缓存内容：{}", value);
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        log.info("执行removeObject方法移除缓存。");
        getRedisTemplate().opsForHash().delete(id, key.toString());
        return null;
    }

    @Override
    public void clear() {
        log.info("执行clear方法清空缓存。");
        getRedisTemplate().delete(id.toString());//清空缓存
    }

    @Override
    public int getSize() {
        //获取hash中的key-value的数量
        int num = getRedisTemplate().opsForHash().size(id).intValue();
        log.info("获取缓存中键值对的数量：", num);
        return num;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    //封装redisTemplate
    private RedisTemplate getRedisTemplate() {
        //获取redisTemplate
        RedisTemplate redisTemplate = (RedisTemplate) SpringBeanUtil.getBean("aaaa");
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}