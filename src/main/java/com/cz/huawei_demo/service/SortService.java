package com.cz.huawei_demo.service;

import com.cz.huawei_demo.dao.SortMapper;
import com.cz.huawei_demo.entity.Sort;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortService {
    @Autowired
    SortMapper sortMapper;

    public Result getSortList(){
        List<Sort> sortList = sortMapper.getSortList();
        return new Result(200,"请求成功，请求到了" + sortList.size() + "条数据" ,sortList);
    }
}
