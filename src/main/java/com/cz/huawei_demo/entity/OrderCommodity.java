package com.cz.huawei_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCommodity implements Serializable {
    private Integer orderCommodityId;//订单关联商品的唯一标识
    private Integer belongOrderId;//所属订单的id
    private Integer commodityId;//当前关联商品的id
    private Integer commodityNumber;//当前订单关联商品的数量
    private String commodityOptions;//当前订单关联商品的用户选择的配置信息
    private int commodityPrice;//当前订单关联商品的根据用户选择不同配置的价格
    private String commodityImg;
}
