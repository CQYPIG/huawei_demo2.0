package com.cz.huawei_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCarCache implements Serializable {
    private Integer shopCarId;
    private Integer commodityNumber;
    private String options;
}
