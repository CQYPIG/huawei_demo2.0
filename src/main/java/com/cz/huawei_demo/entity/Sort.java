package com.cz.huawei_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sort implements Serializable {
    private Integer id;
    private String sort;
    private List<Commodity> commodityList;
    private List<SortImg> imgList;
}
