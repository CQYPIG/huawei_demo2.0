package com.cz.huawei_demo.controller;


import com.cz.huawei_demo.entity.Order;
import com.cz.huawei_demo.entity.User;
import com.cz.huawei_demo.service.CommodityService;
import com.cz.huawei_demo.service.UserService;
import com.cz.huawei_demo.until.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@PropertySource(value = {"classpath:redis.properties"})
public class UserController {

    @Value("${host}")
    private String host;

    @Value("${password}")
    private String password;

    @Autowired
    UserService userService;

    @Autowired
    CommodityService commodityService;

    //用户登录
    @GetMapping("/user/{userName}/{password}")
    public Result login(User user, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        return userService.login(user,session);
    }
    @GetMapping("/pay/{commodityId}")
    public Result pay(@PathVariable("commodityId") String commodityId){
        return commodityService.getCommodity(commodityId);
    }

    //检查token
    @GetMapping("/checkToken")
    public Result checkToken(){
        return new Result(200,"成功",null);
    }

    //退出登录
    @GetMapping("/loginOut")
    public Result loginOut(HttpServletRequest httpServletRequest){
        Result result = new Result();
        JedisPool jedisPool = new JedisPool(host,6379);
        Jedis jedis = jedisPool.getResource();
        jedis.auth(password);
        String token = httpServletRequest.getHeader("token");
        if(token != null && !"".equals(token)){
            log.info(token+"用户退出了登录");
            token = token.substring(0,token.length()-1);
            jedis.del(token);
            result.setResponseCode(200);
            result.setMessage("退出成功");
        }
        return result;
    }

    //用户注册
    @PostMapping("/register/{username}/{password}")
    public Result register(@PathVariable("username")String username, @PathVariable("password")String password){
        return userService.userRegister(username,password);
    }

    @PostMapping("/checkUsername/{username}")
    public boolean checkUsername(@PathVariable("username")String username){
        return userService.checkUsername(username);
    }




}
