package com.cz.huawei_demo.controller;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.cz.huawei_demo.dao.OrderCommodityMapper;
import com.cz.huawei_demo.dao.OrderMapper;
import com.cz.huawei_demo.entity.Order;
import com.cz.huawei_demo.entity.OrderCommodity;
import com.cz.huawei_demo.service.OrderService;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class OrderController {
    @Autowired
    OrderService orderService;


    /*根据用户id查询用户所有订单*/
    @GetMapping("/order/{userId}")
    public Result getOrderByUserId(@PathVariable("userId")String userId){
        return orderService.getOrderByUserId(userId);
    }

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderCommodityMapper orderCommodityMapper;
    //添加单条商品
    @PostMapping("/order")
    public Result addOrder(Order order,OrderCommodity orderCommodity){
        System.out.println("@@@@@@@"+orderCommodity);
        //订单的添加和订单关联商品的添加应该同时完成，所以使用springboot的事务处理
        try {
            //对order和orderCommodity两张表的操作都在service中
            return orderService.addOrder(order, orderCommodity);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(201,"数据异常",null);
        }
    }

    //从购物车中添加多条商品
    @PostMapping("/addOrderOfShopCar")
    public Result addOrderOfShopCar(Order order,String SCCommodityList){
        //处理接收到的json格式字符串
        JSONArray jsonArray = JSONArray.parseArray(SCCommodityList);
        //使用fastJson2 alibaba
        String[] shopCarIds = jsonArray.to(String[].class);

        System.out.println("!!@!@!!!!!!!"+order.getUserId());

        try {
            return orderService.addSCCommodityToOrder(order, shopCarIds);
        } catch (Exception e) {
            System.out.println("addOrderOfShopCar事务触发");
            e.printStackTrace();
            return new Result(201,"操作失败，事务触发",null);
        }
    }
    //订单支付
    @PostMapping("/payOrder")
    public Result payOrder(String orderId,String userId){
        System.out.println("###########"+userId);
        return orderService.payOrder(orderId,userId);
    }
    //订单取消
    @PostMapping("/cancelOrder")
    public Result cancelOrder(String orderId,String userId){
        System.out.println("###########"+userId);
        return orderService.cancelOrder(orderId,userId);
    }
    //订单支付
    @PostMapping("/deleteOrder")
    public Result deleteOrder(String orderId,String userId){
        System.out.println("###########"+userId);
        return orderService.deleteOrder(orderId,userId);
    }
}
