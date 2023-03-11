package com.cz.huawei_demo.controller;

import com.cz.huawei_demo.service.ImgService;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImgController {
    @Autowired
    ImgService imgService;
    /*获取左侧菜单栏*/
//    @GetMapping("/getCategoryList")
//    public Result getCategoryList(){
//        return imgService.getCategoryList();
//    }

    /*获取轮播图*/
    @GetMapping("/getBanners")
    public Result getBanners(){
        return imgService.getBanners();
    }

}
