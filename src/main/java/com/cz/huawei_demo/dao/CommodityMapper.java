package com.cz.huawei_demo.dao;


import com.cz.huawei_demo.entity.Commodity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
//@CacheNamespace
public interface CommodityMapper{
    //根据id从数据库中查询商品信息  -->  对options字段的封装放到service中执行
    Commodity getCommodityById(@Param("id")String id);

    //获取秒杀商品列表
    List<Commodity> getSeckillCommodity();

    //商品库存减少
    int repertoryDecr(@Param("number")String number,@Param("commodityId")String commodityId);

    //根据商品id查询商品库存  没有使用缓存
    @Select("select repertory from commodity where id = #{commodityId}")
    int getRepertoryById(@Param("commodityId")String commodityId);

    //关键词模糊搜索商品
    List<Commodity> likeCommodity(@Param("keyWord")String keyWord);


}
