package com.cz.huawei_demo.service;

import com.cz.huawei_demo.dao.OrderCommodityMapper;
import com.cz.huawei_demo.dao.OrderMapper;
import com.cz.huawei_demo.dao.ShopCarMapper;
import com.cz.huawei_demo.entity.Order;
import com.cz.huawei_demo.entity.ShopCarCommodity;
import com.cz.huawei_demo.until.MyUtils;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ShopCarService {
    @Autowired
    ShopCarMapper shopCarMapper;
    @Autowired
    MyUtils myUtils;
    @Autowired
    MyUtils utils;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    OrderMapper orderMapper;

    //添加购物车商品
    public Result addShopCarCommodity(ShopCarCommodity shopCarCommodity){
        //设置对应商品的图片
        String s = myUtils.HandleString(shopCarCommodity.getScCommodityOptions());
        String colorUrl = shopCarMapper.getColorUrl(shopCarCommodity.getScCommodityId().toString(), s);
        System.out.println("获取到的图片路径" + colorUrl);
        shopCarCommodity.setScCommodityImg(colorUrl);
        int i = shopCarMapper.addShopCar(shopCarCommodity);
        List<ShopCarCommodity> allSCCommodity = shopCarMapper.getAllSCCommodity(shopCarCommodity.getUserId().toString());
        if (i != 0){
            return new Result(200,"添加成功",allSCCommodity);
        }
        return new Result(201,"添加失败",allSCCommodity);
    }

    /*根据用户id查询所有购物车商品*/
    public Result getAllSCCommodity(String userId){
        List<ShopCarCommodity> allSCCommodity = shopCarMapper.getAllSCCommodity(userId);
        return new Result(200,"请求到了" + allSCCommodity.size() + "数据",allSCCommodity);
    }


    /*根据id删除指定购物车商品*/
    public Result deleteShopCar(String shopCarId,String userId){
        int i = shopCarMapper.deleteShopCar(shopCarId);
        List<ShopCarCommodity> allSCCommodity = shopCarMapper.getAllSCCommodity(userId);
        return  new Result(200,i+"条数据被删除",allSCCommodity);
    }

    @Transactional
    public Result deleteShopCars(String[] shopCarIds,String userId){
        for (String shopCarId:shopCarIds){
            deleteShopCar(shopCarId,userId);
        }
        List<ShopCarCommodity> allSCCommodity = shopCarMapper.getAllSCCommodity(userId);
        return  new Result(200,shopCarIds.length+"条数据被删除",allSCCommodity);
    }

    public Result changeCommodityNumber(String commodityNumber,String shopCarId,String userId){
//        int priceByShopCarId = shopCarMapper.getPriceByShopCarId(shopCarId);
        int i = shopCarMapper.changeCommodityNumber(commodityNumber, shopCarId);
        List<ShopCarCommodity> allSCCommodity = shopCarMapper.getAllSCCommodity(userId);
        return new Result(200,i+"条数据被更新",allSCCommodity);
    }


}
