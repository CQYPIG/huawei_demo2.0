package com.cz.huawei_demo.controller;


import com.cz.huawei_demo.entity.Order;
import com.cz.huawei_demo.entity.OrderCommodity;
import com.cz.huawei_demo.service.LuaSecKillService;
import com.cz.huawei_demo.service.SecKillService;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class SecKillController {

    @Autowired
    SecKillService secKillService;
    @Autowired
    LuaSecKillService luaSecKillService;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/secKill")
    public Result doSecKill(Order order, OrderCommodity orderCommodity){
//        return secKillService.doSecKill(commodityId,userId);
        System.out.println(order);
        System.out.println(orderCommodity);
        return luaSecKillService.luaSecKill(order,orderCommodity);
//        return null;
    }

    //没有添加第三方支付模块的时候用于辅助秒杀逻辑的
    //判断秒杀开始了没
    @GetMapping("/checkSeckill")
    public boolean checkSeckill(String orderId){
        String key = "COMMODITY:"+orderId+":KC";
        Object value = redisTemplate.opsForValue().get(key);
        return value == null;
    }




}
