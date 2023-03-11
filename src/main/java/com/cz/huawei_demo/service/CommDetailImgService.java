package com.cz.huawei_demo.service;


import com.cz.huawei_demo.dao.CommDetailImgMapper;
import com.cz.huawei_demo.entity.CommodityDetailImg;
import com.cz.huawei_demo.until.Result;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommDetailImgService {
    @Autowired
    CommDetailImgMapper commDetailImgMapper;
    public Result getImgByCommId(String id){
        CommodityDetailImg images = commDetailImgMapper.getImgByCommId(id);
        if (images != null){
            return new Result(200,"请求到了" + images.getImages().size() + "条数据" ,images);
        }
        return new Result(200,"暂无图片" ,null);
    }

    /*根据商品颜色和id返回图片列表*/
    public Result getImages(@Param("commodityId")String commodityId, @Param("color")String color){
        List<String> images = commDetailImgMapper.getImages(commodityId, color);
        return new Result(200,"请求成功,获取到了" + images.size()+"条数据",images);
    }
}
