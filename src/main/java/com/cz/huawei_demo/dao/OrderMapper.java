package com.cz.huawei_demo.dao;


import com.cz.huawei_demo.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<Order> getOrderByUserId(@Param("userId")String userId);

    /*
    需要注意的是，在MyBatis中添加操作返回的是记录数并非记录主键id。
    因此，如果需要获取新添加记录的主键值，需要在执行添加操作之后，直接读取Java对象的主键属性。
     */
    int addOrder(Order order);

    /*当订单超时未支付时修改订单的有效性*/
    int updateYxState(@Param("orderId")String orderId);

    /*订单支付 修改订单状态*/
    int payOrder(@Param("orderId")String orderId);

    /*取消订单*/
    int cancelOrder(@Param("orderId")String orderId);

    /*删除订单*/
    int deleteOrder(@Param("orderId")String orderId);

}
