package com.cz.huawei_demo.dao;

import com.cz.huawei_demo.entity.Sort;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
/**
 * 对商品分类以及下面所有分类进行查询
 * @author yingfeng
 * @date 2022/10/9 20:49
 */
public interface SortMapper {
    //查询出商品分类列表以及分类下所有商品
    List<Sort> getSortList();
}
