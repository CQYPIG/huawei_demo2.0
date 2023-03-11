package com.cz.huawei_demo.dao;

import com.cz.huawei_demo.entity.CommodityDetailImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommDetailImgMapper {
    /*根据id查找商品详情展示页面图片地址*/
    CommodityDetailImg getImgByCommId(@Param("commodityId")String id);

    /*根据商品颜色和id返回图片列表*/
    List<String> getImages(@Param("commodityId")String commodityId,@Param("color")String color);

}
