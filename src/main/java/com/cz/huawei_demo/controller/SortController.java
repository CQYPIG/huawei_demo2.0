package com.cz.huawei_demo.controller;


import com.cz.huawei_demo.service.SortService;
import com.cz.huawei_demo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SortController {
    @Autowired
    SortService sortService;
    @GetMapping("/getSortList")
    public Result getSortList(){
        return sortService.getSortList();
    }

}
