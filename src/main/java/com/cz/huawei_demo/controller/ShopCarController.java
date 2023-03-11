package com.cz.huawei_demo.controller;


import com.cz.huawei_demo.entity.OrderCommodity;
import com.cz.huawei_demo.entity.ShopCarCommodity;
import com.cz.huawei_demo.service.ShopCarService;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialException;
import java.util.Arrays;

@RestController
public class ShopCarController {

    @Autowired
    ShopCarService shopCarService;
    //购物车添加商品
    @PostMapping("/addShopCar")
    public Result addShopCar(ShopCarCommodity shopCarCommodity){
        return shopCarService.addShopCarCommodity(shopCarCommodity);
    }

    //根据用户id获取用户购物车所有商品
    @GetMapping("/getAllShopCarCommodity/{userId}")
    public Result getAllSCCommodity(@PathVariable("userId") String userId){
        return shopCarService.getAllSCCommodity(userId);
    }

    //使用直接操作数据库的方式
    @GetMapping("/changeCommodityNumber/{commodityNumber}/{shopCarId}/{userId}")
    public Result changeCommodityNumber(@PathVariable("commodityNumber") String commodityNumber,
                                        @PathVariable("shopCarId") String shopCarId,
                                        @PathVariable("userId")String userId){
        return shopCarService.changeCommodityNumber(commodityNumber,shopCarId,userId);
    }

    @GetMapping("/deleteShopCar/{shopCarId}/{userId}")
    public Result deleteShopCar(@PathVariable("shopCarId")String shopCarId,@PathVariable("userId")String userId){
        return shopCarService.deleteShopCar(shopCarId,userId);
    }

    //删除用户选中的购物车商品
    @PostMapping("deleteShopCars")
    public Result deleteShopCars(String[] SCCommodityList,String userId){
        try {
            return shopCarService.deleteShopCars(SCCommodityList,userId);
        } catch (Exception e) {
            return new Result(201,"删除失败",null);
        }

    }


}
