package com.cz.huawei_demo.dao;


import com.cz.huawei_demo.entity.OrderCommodity;
import com.cz.huawei_demo.entity.ShopCarCommodity;
import com.cz.huawei_demo.entity.vo.ShopCarCommodityVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopCarMapper {



    //添加购物车
    int addShopCar(ShopCarCommodity shopCarCommodity);

    /*根据用户id查询用户的所有商品*/
    List<ShopCarCommodity> getAllSCCommodity(@Param("userId")String UserId);

    /*根据商品id获取logo图*/
    String getColorUrl(@Param("commodityId")String commodityId,@Param("color")String color);

    /*用户修改商品数量*/
    /*修改商品数量的时候会伴随着商品价格的变化*/
    int changeCommodityNumber(@Param("commodityNumber")String commodityNumber,@Param("shopCarId")String shopCarId);

    /*根据id删除指定商品*/
    int deleteShopCar(@Param("shopCarId")String shopCarId);

    /*购物车商品提交时  将购物车商品信息转存到订单关联商品中*/
    int addOrderCommodityFromShopCar(@Param("orderId")String orderId,@Param("shopCarId")String shopCarId);

    /*根据购物车商品id查询商品数量*/
    @Select("select sc_commodity_number from shop_car where shop_car_id = #{shopCarId}")
    int getCommodityNumberById(@Param("shopCarId")String shopCarId);

    /*根据购物车商品id关联商品表只查出商品库存以及购车中商品的数量*/
    ShopCarCommodityVo getShopCarCommodityById(@Param("shopCarId")String shopCarId);
}
