package com.cz.huawei_demo.service;


import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.xml.ws.Action;
import java.util.List;

@Service
@PropertySource(value = {"classpath:redis.properties"})
public class SecKillService {
    @Autowired
    OrderService orderService;
    @Autowired
    Jedis jedis;

    @Value("${host}")
    private String host;
    @Value("${password}")
    private String password;

    public Result doSecKill(String commodityId,String userId){


        Jedis jedis = null;
        try {
            JedisPool jedisPool = new JedisPool(host,6379);
            jedis = jedisPool.getResource();
            jedis.auth(password);
        } catch (Exception e) {
            return new Result(200,"获取连接失败了",null);
        }

        //redis中 商品库存 键的拼接
        String commodityKey = "COMMODITY:" + commodityId+":KC";
        //redis中 秒杀成功用户的set集合的键
        String successUserKey = commodityId + ":SUCCESS:USER";
        //从redis中获取商品库存
        String result = jedis.get(commodityKey);
//        秒杀是否开始（数据库中有没有相关信息）
        if(result == null){
            System.out.println("秒杀还没开始");
            jedis.close();
            return new Result(201,"秒杀还没开始",null);
        }
//        秒杀是否结束（库存是否为0）
        if(Integer.parseInt(result) <= 1){
            System.out.println("秒杀已结束");
            jedis.close();
            return new Result(201,"秒杀已结束",null);
        }

//        用户是否重复秒杀-->将秒杀成功的用户的信息存放在redis中的
        if(jedis.sismember(successUserKey, userId)){
            System.out.println("不能重复秒杀");
            jedis.close();
            return new Result(201,"不能重复秒杀",null);
        }


//        秒杀成功->库存-1->成功用户信息写入->订单信息持久化
        //使用redis中的watch
        jedis.watch(commodityKey);
        Transaction multi = jedis.multi();
        //成功用户信息写入
        multi.sadd(successUserKey,userId);
        //库存-1
        multi.decr(commodityKey);
        List<Object> exec = multi.exec();
        if (exec == null || exec.size() == 0){
            jedis.close();
            System.out.println("内别人抢先啦！");
            return new Result(200,"活动太火爆啦！请稍后重试",null);
        }
        jedis.close();
        System.out.println("秒杀成功");
        return new Result(200,"秒杀成功",null);
    }
}
