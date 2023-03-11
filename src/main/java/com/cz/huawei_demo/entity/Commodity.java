package com.cz.huawei_demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Commodity implements Serializable {
    private Integer id;//商品唯一标识
    private String commodityName;//商品名称
    private String describe;//商品描述
    private int price;//商品价格
    private int nowPrice;//打折的时候的价格
    private String imgUrl;//主页展示预览图片地址
    private Integer repertory;//这个设置为商品的库存
    private Integer limitNumber;//限购数量
    //数据库中查询出来的所有配置信息
    /**
     * 数据库中的option字段字符串必须按照
     * 颜色：冰霜-/imgeas/、密语-/imgeas/$套餐：单品、听书vip（季卡）、超级音乐vip（季卡）
     * 这种格式
     */
    private String options;
    //封装的返回到前端的json格式数据
    private List<CommodityOption> jsonOptions;
}
