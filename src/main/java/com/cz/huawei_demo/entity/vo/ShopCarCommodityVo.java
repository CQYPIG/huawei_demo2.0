package com.cz.huawei_demo.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *  封装从购物车表和商品表中关联查出来的购物车商品数量和商品库存
 * @author yingfeng
 * @date 2022/11/1 0:34
 */
@Data
public class ShopCarCommodityVo{
    private String shopCarCommodityNumber;
    private String commodityRepertory;

    public boolean isEnough(){
        int number = Integer.parseInt(shopCarCommodityNumber);
        int repertory = Integer.parseInt(commodityRepertory);
        return number<repertory;
    }
}
