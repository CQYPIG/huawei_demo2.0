package com.cz.huawei_demo.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class SortImg implements Serializable {
    private Integer sortImgId;
    private String sortName;
    private String sortImgUrl;
}
