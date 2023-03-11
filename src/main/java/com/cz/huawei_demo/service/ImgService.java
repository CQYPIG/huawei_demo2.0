package com.cz.huawei_demo.service;


import com.cz.huawei_demo.dao.ImgMapper;
import com.cz.huawei_demo.entity.Banner;
import com.cz.huawei_demo.entity.CateGory;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImgService {
    @Autowired
    ImgMapper imgMapper;
//    public Result getCategoryList(){
//        List<CateGory> categoryList = imgMapper.getCategoryList();
//        if (categoryList.size() == 0){
//            return new Result(201,"网络故障，请稍后重试",null);
//        }
//        return new Result(200,"请求到了：" + categoryList.size() + "条数据",categoryList);
//    }

    /*返回主页所有的轮播图*/
    public Result getBanners(){
        List<Banner> banners = imgMapper.getBanners();
        List<Banner> banners1 = imgMapper.getBanners();
        List<Banner> banners2 = imgMapper.getBanners();
        if (banners.size() == 0){
            return new Result(201,"网络故障，请稍后重试",null);
        }
        return new Result(200,"请求到了：" + banners.size() + "条数据",banners);
    }
}
