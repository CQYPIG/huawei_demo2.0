package com.cz.huawei_demo.service;


import com.cz.huawei_demo.dao.CommodityMapper;
import com.cz.huawei_demo.entity.Commodity;
import com.cz.huawei_demo.entity.CommodityOption;
import com.cz.huawei_demo.entity.Option;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

@Service
public class CommodityService {
    @Autowired
    CommodityMapper commodityMapper;
    public Result getCommodityById(String commodityId){
        Commodity commodity = commodityMapper.getCommodityById(commodityId);
        String options = commodity.getOptions();
        //颜色：冰霜-/imgeas/、密语-/imgeas/$套餐：单品、听书vip（季卡）、超级音乐vip（季卡）
        String[] first = options.split("&");
        TreeMap<String, List<String>> optionTreeMap = new TreeMap<String, List<String>>();
        ArrayList<CommodityOption> jsonOptions = new ArrayList<>();
        for (int i=0 ; i<first.length ; i++){
            //处理中文字符的冒号
            String[] second = first[i].split("：");
            //也有可能是英文字符的，这样数据库中对冒号的中英文就不做要求
            if (second.length == 0){
                second = first[i].split(":");
            }
            /* 返回到前端的数据因该是这样的格式的 List<CommodityOption> jsonOptions;
            *  这里就是在new这个list,例如将颜色的配置执行完毕之后将颜色这个CommodityOption放进list集合中
            */
            CommodityOption commodityOption = new CommodityOption();
            /* 在颜色这个CommodityOption种存放的是多条配置 List<Option> options;
            *  这个temp就是用来存放颜色这样的一系列配置的
            * */
            ArrayList<Option> temp = new ArrayList<>();
            for (int j = 0; j < second.length; j++) {
                if (j ==0){
                    commodityOption.setOptionName(second[j]);
                } else {
                    String[] option = second[j].split("、");
                    /*  处理每一条配置的单独信息，因为有的配置是需要加钱的，所以我自己添加了一个实体类。
                    * */

                    for(int index = 0;index < option.length;index++){
                        String[] split = option[index].split("-");
                        Option optionTemp = new Option();
                        if (split.length == 1){
                            optionTemp.setOption(split[0]);
                        } else {
                            optionTemp.setOption(split[0]);
                            optionTemp.setOptionPrice(split[1]);
                        }
                        temp.add(optionTemp);
                    }

                    commodityOption.setOptions(temp);
                }
            }
            jsonOptions.add(commodityOption);
        }
        commodity.setJsonOptions(jsonOptions);
        return new Result(200,"请求成功",commodity);
    }

    public Result getCommodity(String commodityId){
        Commodity commodity = commodityMapper.getCommodityById(commodityId);
        if (commodity != null){
            return new Result(200,"请求成功",commodity);
        }
        return new Result(201,"请求失败",null);
    }

    //获取秒杀商品列表
    public Result getSeckillCommodity(){
        List<Commodity> seckillCommodity = commodityMapper.getSeckillCommodity();
        return new Result(200,"获取到了" + seckillCommodity.size()+"条数据",seckillCommodity);
    }

    //模糊查找商品
    public Result likeGet(String keyWord){
        List<Commodity> commodities = commodityMapper.likeCommodity(keyWord);
        Result result = new Result();
        result.setResult(commodities);
        result.setResponseCode(200);
        result.setMessage("请求到了" + commodities.size() + "条数据");
        return result;
    }
}
