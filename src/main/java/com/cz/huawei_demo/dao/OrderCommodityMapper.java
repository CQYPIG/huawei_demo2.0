package com.cz.huawei_demo.dao;


import com.cz.huawei_demo.entity.OrderCommodity;
import com.cz.huawei_demo.entity.ShopCarCommodity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderCommodityMapper {

    /*首先是添加购物车*/


    /*根据用户id查询购物车*/
//    List<OrderCommodity> getShopCarByUserId(@Param("userId")String uerId);

    int addOrderCommodity(OrderCommodity orderCommodity);



    /*添加订单的时候将详情表中对应的能标识出商品的图片赋值给这个订单*/
    int setLogoImg(@Param("commodityId")String commodityId,@Param("color") String color,@Param("OrderCommodityId")String OrderCommodityId);
}
