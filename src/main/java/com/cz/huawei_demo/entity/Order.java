package com.cz.huawei_demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private Integer orderId;//订单唯一标识
    private Integer userId;//订单关联的用户id
    private int orderPrice;//订单价格
    private String orderTime;//订单创建时间
    private boolean yx;//有效
    private boolean dfk;//待付款
    private boolean dsh;//待收货
    //原来
    //向前端返回订单列表的时候订单关联商品通过Commodity来接受，多个commodity通过list封装
    private List<Commodity> commodityList;
    //现在想要用这个来接受前端发送的购物车里面的订单以及订单下所有关联商品的信息
    private List<OrderCommodity> orderCommodityList;
}
