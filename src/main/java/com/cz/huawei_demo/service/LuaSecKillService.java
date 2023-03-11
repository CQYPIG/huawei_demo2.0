package com.cz.huawei_demo.service;

import com.cz.huawei_demo.entity.Order;
import com.cz.huawei_demo.entity.OrderCommodity;
import com.cz.huawei_demo.redis.utils.Lua;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@PropertySource(value = {"classpath:redis.properties"})
public class LuaSecKillService {

    @Value("${host}")
    private String host;
    @Value("${password}")
    private String password;
    @Autowired
    OrderService orderService;
    public Result luaSecKill(Order order, OrderCommodity orderCommodity){
        Jedis jedis = null;
        try {
            JedisPool jedisPool = new JedisPool(host, 6379);
            jedis = jedisPool.getResource();
            jedis.auth(password);
        } catch (Exception e) {
            System.out.println("当前活动太火爆啦！请稍后再试");
            return new Result(204,"当前活动太火爆啦！请稍后再试",null);
        }
        try{
            Object eval = jedis.eval(Lua.secKillScript,2, orderCommodity.getCommodityId()+"", order.getUserId()+"");
            int result = Integer.parseInt(eval.toString());
            if (result ==0 ){
                System.out.println("秒杀已经结束！！");
                //需要及时的关闭连接，否则只依靠默认的超时时间的话，会导致连接池中连接用光，连接失败。
                jedis.close();
                return new Result(201,"秒杀已经结束",null);
            }else if (result == 1){
                System.out.println("秒杀成功！");
                //秒杀成功之后添加订单信息
                orderService.addOrder(order,orderCommodity);
                jedis.close();
                return new Result(200,"秒杀成功",null);
            }else if (result == 2){
                System.out.println("不能重复抢购！");
                jedis.close();
                return new Result(202,"不能重复抢购",null);
            }
            System.out.println("@@@@@@@@@@@未知错误@@@@@@@@@@@@@@");
            jedis.close();
            return new Result(204,"活动太火爆啦！请稍后再试",null);
        }
        catch (Exception e){
//            e.printStackTrace();
            System.out.println("lua报错了");
            jedis.close();
            return new Result(203,"秒杀还没开始",null);
        }

    }
}
