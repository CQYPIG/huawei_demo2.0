package com.cz.huawei_demo.service;


import com.cz.huawei_demo.dao.CommodityMapper;
import com.cz.huawei_demo.dao.OrderCommodityMapper;
import com.cz.huawei_demo.dao.OrderMapper;
import com.cz.huawei_demo.dao.ShopCarMapper;
import com.cz.huawei_demo.entity.Order;
import com.cz.huawei_demo.entity.OrderCommodity;
import com.cz.huawei_demo.entity.ShopCarCommodity;
import com.cz.huawei_demo.entity.vo.ShopCarCommodityVo;
import com.cz.huawei_demo.until.MyUtils;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *  自定义的空指针异常 约定：有商品库存不足，抛出异常是为了让事务回滚  -->后来弃用改为方式二
 *  补充：springboot中手动事务回滚有两种方式
 *      ①：抛出一个异常，注意异常不能被catch，catch了相当于没有异常
 *      ②：调用TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
 *          后续代码还是会执行
 * @author yingfeng
 * @date 2022/11/1 1:10
 */
@Service
public class OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderCommodityMapper orderCommodityMapper;
    @Autowired
    MyUtils utils;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    MyUtils myUtils;
    @Autowired
    ShopCarMapper shopCarMapper;
    @Autowired
    CommodityMapper commodityMapper;

    private int orderTimeOut = 10;

    @Transactional
    public Result addOrder(Order order, OrderCommodity orderCommodity){
        //结果
        Result result = new Result();
        //设置订单生成时间
        String formatTime = utils.getFormatTime();
        order.setOrderTime(formatTime);
        //设置订单是否待收货，付款之后才会待收货。
        order.setDsh(!order.isDfk());
        order.setYx(true);

        //向订单表中添加当前订单信息，并将生成的主键id返回给传递进来的order中的id
        int orderResult = orderMapper.addOrder(order);

        //如果未付款就将使用向jedis中添加数据并设置有效时间
        if (order.isDfk()){
            //字符串拼接
            String key = "ORDER:" + order.getOrderId();
            String value = order.getOrderId().toString();
            try {
                redisTemplate.opsForValue().set(key, value, orderTimeOut, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(201,"redis连接失败",null);
            }
        }else{
            //如果付款成功，库存减少
            //首先判断当前库存是否足够
            int repertory = commodityMapper.getRepertoryById(orderCommodity.getCommodityId().toString());
            //判断库存是否足够
            if(repertory < orderCommodity.getCommodityNumber()){
                result.setResponseCode(201);
                result.setMessage("库存不足！");
                //库存不足的时候手动回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            commodityMapper.repertoryDecr(orderCommodity.getCommodityNumber().toString(), orderCommodity.getCommodityId().toString());
            result.setResponseCode(200);
            result.setMessage("购买成功！");
        }

        //将order生成的主键id赋值给orderCommodity表中的参数orderCommodity中的belongOrderId
        orderCommodity.setBelongOrderId(order.getOrderId());
        int i = orderCommodityMapper.addOrderCommodity(orderCommodity);

        //获取当前订单中关联商品的颜色配置（字符串）
        String s = myUtils.HandleString(orderCommodity.getCommodityOptions());

        //数据库操作将查询出来的对应颜色图片地址赋值非订单关联商品
        int i1 = orderCommodityMapper.setLogoImg(orderCommodity.getCommodityId().toString(), s,
                orderCommodity.getOrderCommodityId().toString());

        List<ShopCarCommodity> allSCCommodity = shopCarMapper.getAllSCCommodity(order.getUserId().toString());
        //return
        if(i != 0 && orderResult != 0){
            return new Result(200,"请求成功",allSCCommodity);
        }else {
            return result;
        }

    }

 /*将购物车中选中商品添加到订单中*/
    /*订单的添加和订单关联商品的添加要保证数据完整性，要么都成功，要么都失败，使用spring的事务管理*/
    @Transactional
    public Result addSCCommodityToOrder(Order order,String[] shopCarIds){
        //跟不同添加订单一样还是先生成订单的生成事件
        //设置订单生成时间
        String formatTime = utils.getFormatTime();
        order.setOrderTime(formatTime);

        //根据是否付款初始化订单状态
        order.setDsh(!order.isDfk());
        order.setYx(true);

        //添加订单  返回的订单自动生成的id再order中
        int addOrderResult = orderMapper.addOrder(order);
        //如果未付款,使用redis处理订单失效事件
        if (order.isDfk()){
            //字符串拼接
            String key = "ORDER:" + order.getOrderId();
            String value = order.getOrderId().toString();
            System.out.println("将订单超时时间加入redis中");
            try {
                redisTemplate.opsForValue().set(key, value, orderTimeOut, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(201,"redis连接失败",null);
            }
        }else{
            Result result = new Result();
            //处理订单中每一个商品的库存减少
            for (String shopCarId : shopCarIds) {
                //处理购物车中选中的多条商品
                ShopCarCommodityVo vo = shopCarMapper.getShopCarCommodityById(shopCarId);
                if(vo.isEnough()){
                    //库存充足，库存减少
                    commodityMapper.repertoryDecr(vo.getShopCarCommodityNumber(),shopCarId);
                }else{
                    result.setMessage("id:" + shopCarId + "的商品库存不足");
                    result.setResponseCode(201);
                    //库存不足的时候手动回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                //将购物车中的信息转存到订单关联商品表中
                int addResult = shopCarMapper.addOrderCommodityFromShopCar(order.getOrderId().toString(), shopCarId);
                //转存成功之后再购物车中删除这条商品
                int deleteResult = shopCarMapper.deleteShopCar(shopCarId);
            }
        }


        for (String shopCarId : shopCarIds) {
            //处理购物车中选中的多条商品
            //将购物车中的信息转存到订单关联商品表中
            int addResult = shopCarMapper.addOrderCommodityFromShopCar(order.getOrderId().toString(), shopCarId);
            //转存成功之后再购物车中删除这条商品
            int deleteResult = shopCarMapper.deleteShopCar(shopCarId);
        }

        List<ShopCarCommodity> allSCCommodity = shopCarMapper.getAllSCCommodity(order.getUserId().toString());

        return new Result(200,"成功添加"+shopCarIds.length+"条商品",allSCCommodity);

    }

    public Result getOrderByUserId(String userId){
        List<Order> orders = orderMapper.getOrderByUserId(userId);
        if (orders.size() != 0){
            return new Result(200,"获取到"+orders.size()+"条数据",orders);
        }
        return new Result(201,"请求失败",null);
    }

    //订单支付
    public Result payOrder(String orderId,String userId){
        int i = orderMapper.payOrder(orderId);
        List<Order> orders = orderMapper.getOrderByUserId(userId);
        Result result = new Result();
        if(i == 1) {
            result.setResponseCode(200);
            result.setMessage("付款成功");
        }else {
            result.setResponseCode(201);
            result.setMessage("付款失败");
        }
        result.setResult(orders);
        return result;
    }
    //取消订单
    public Result cancelOrder(String orderId,String userId){
        int i = orderMapper.cancelOrder(orderId);
        List<Order> orders = orderMapper.getOrderByUserId(userId);
        Result result = new Result();
        if(i == 1) {
            result.setResponseCode(200);
            result.setMessage("取消成功");
        }else {
            result.setResponseCode(201);
            result.setMessage("取消失败");
        }
        result.setResult(orders);
        return result;
    }

    //删除订单
    public Result deleteOrder(String orderId,String userId){
        int i = orderMapper.deleteOrder(orderId);
        List<Order> orders = orderMapper.getOrderByUserId(userId);
        Result result = new Result();
        if(i == 1) {
            result.setResponseCode(200);
            result.setMessage("删除成功");
        }else {
            result.setResponseCode(201);
            result.setMessage("删除失败");
        }
        result.setResult(orders);
        return result;
    }
}
