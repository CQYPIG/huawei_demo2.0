package com.cz.huawei_demo.dao;


import com.cz.huawei_demo.entity.Banner;
import com.cz.huawei_demo.entity.CateGory;
import com.cz.huawei_demo.entity.Commodity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@CacheNamespace(implementation = com.cz.huawei_demo.redis.config.RedisCacheConfig.class)
public interface ImgMapper {
    /*获取主页导航栏的图片*/
//    @Select("select * from categoryname")
//    List<CateGory> getCategoryList();

    /*获取主页轮播图*/
    @Select("select * from banners")
    List<Banner> getBanners();

}
