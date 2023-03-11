package com.cz.huawei_demo;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.cz.huawei_demo.dao.*;
import com.cz.huawei_demo.entity.*;
import com.cz.huawei_demo.entity.vo.ShopCarCommodityVo;
import com.cz.huawei_demo.redis.utils.JsonUtils;
import com.cz.huawei_demo.service.ImgService;
import com.cz.huawei_demo.until.Result;
import com.cz.huawei_demo.until.SpringBeanUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

//@SpringBootTest
class ApplicationTests {

    @Autowired
    SortMapper sortMapper;
    @Autowired
    CommodityMapper commodityMapper;
    @Autowired
    CommDetailImgMapper commDetailImgMapper;
    

    @Test
    void contextLoads() {
//        Commodity commodity = commodityMapper.getCommodityById("3");
//        String options = commodity.getOptions();
//        //颜色：冰霜-/imgeas/、密语-/imgeas/$套餐：单品、听书vip（季卡）、超级音乐vip（季卡）
//        String[] first = options.split("&");
//        TreeMap<String, List<String>> optionTreeMap = new TreeMap<String, List<String>>();
//        ArrayList<CommodityOption> jsonOptions = new ArrayList<>();
//        for (int i=0 ; i<first.length ; i++){
//            //处理中文字符的冒号
//            String[] second = first[i].split("：");
//            //也有可能是英文字符的，这样数据库中对冒号的中英文就不做要求
//            if (second.length == 0){
//                second = first[i].split(":");
//            }
//            /* 返回到前端的数据因该是这样的格式的 List<CommodityOption> jsonOptions;
//            *  这里就是在new这个list,例如将颜色的配置执行完毕之后将颜色这个CommodityOption放进list集合中
//            */
//            CommodityOption commodityOption = new CommodityOption();
//            /* 在颜色这个CommodityOption种存放的是多条配置 List<Option> options;
//            *  这个temp就是用来存放颜色这样的一系列配置的
//            * */
//            ArrayList<Option> temp = new ArrayList<>();
//            for (int j = 0; j < second.length; j++) {
//                if (j ==0){
//                    commodityOption.setOptionName(second[j]);
//                } else {
//                    String[] option = second[j].split("、");
//                    /*  处理每一条配置的单独信息，因为有的配置是需要加钱的，所以我自己添加了一个实体类。
//                    * */
//
//                    for(int index = 0;index < option.length;index++){
//                        String[] split = option[index].split("-");
//                        Option optionTemp = new Option();
//                        if (split.length == 1){
//                            optionTemp.setOption(split[0]);
//                            System.out.println(optionTemp);
//                        } else {
//                            optionTemp.setOption(split[0]);
//                            optionTemp.setOptionPrice(split[1]);
//                            System.out.println(optionTemp);
//                        }
//                        temp.add(optionTemp);
//                    }
//
//                    commodityOption.setOptions(temp);
//                }
//            }
//            jsonOptions.add(commodityOption);
//            System.out.println(commodityOption);
//        }
//        commodity.setJsonOptions(jsonOptions);
    }






}
