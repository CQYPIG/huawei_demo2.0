package com.cz.huawei_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCarCommodity implements Serializable  {
    private Integer shopCarId;
    private Integer userId;
    private Integer scCommodityId;
    private Integer scCommodityNumber;
    private Integer scCommodityPrice;
    private String scCommodityOptions;
    private String scCommodityImg;
    private Commodity commodity;

}
