package com.cz.huawei_demo.controller;


import com.cz.huawei_demo.service.CommodityService;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommodityController {
    @Autowired
    CommodityService commodityService;

    /*获取商品信息  包括配置参数*/
    @GetMapping("/commodityDetail/{commodityId}")
    public Result commodityDetail(@PathVariable("commodityId")String commodityId){
        return commodityService.getCommodityById(commodityId);
    }

    @GetMapping("/getSeckillCommodity")
    public Result getSeckillCommodity(){
        return commodityService.getSeckillCommodity();
    }

    @GetMapping("/likeGet")
    public Result likeGet(String word){
        return commodityService.likeGet(word);
    }
}
