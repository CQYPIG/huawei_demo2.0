package com.cz.huawei_demo.redis.listenter;

import com.cz.huawei_demo.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class OrderListener extends KeyExpirationEventMessageListener {
    @Autowired
    OrderMapper orderMapper;

    public OrderListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String disabledKey = message.toString();
        String[] split = disabledKey.split(":");
        //如果失效的是订单  就改变订单的是否有效
        if ("ORDER".equals(split[0])){
            //在数据库中根据订单id修改订单的yx状态
            System.out.println("检测到订单" + split[1] + "失效");
            int i = orderMapper.updateYxState(split[1]);
        }
    }
}
