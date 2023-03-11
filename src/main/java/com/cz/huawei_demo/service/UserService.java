package com.cz.huawei_demo.service;


import com.cz.huawei_demo.dao.UserMapper;
import com.cz.huawei_demo.entity.Commodity;
import com.cz.huawei_demo.entity.User;
import com.cz.huawei_demo.until.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpSession;
import java.util.UUID;


@Service
@Slf4j
@PropertySource(value = {"classpath:redis.properties"})
public class UserService {

    private Jedis jedis;

    @Value("${host}")
    private String host;
    @Value("${password}")
    private String password;

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public Result login(User user, HttpSession session){
        User userName = userMapper.findUserName(user);
        if (userName == null){
            return new Result(201,"用户名不存在！",null);
        } else {
            User loginUser = userMapper.login(user);
            if (loginUser != null){
                String token = UUID.randomUUID().toString();
//                session.setAttribute(token,"login");
//                session.setMaxInactiveInterval(60);
                JedisPool jedisPool = new JedisPool(host,6379);
                Jedis jedis = jedisPool.getResource();
                jedis.auth(password);
                jedis.set(token,"isLogin");
                jedis.pexpire(token,60000*30);
                loginUser.setPassword(null);
                loginUser.setToken(token);
                jedis.close();
                //登录成功之后生成token存入session,返回给前端保存。
                return new Result(200,"登录成功",loginUser);
            }
            return new Result(202,"密码错误",null);
        }
    }

    /*用户注册*/
    public Result userRegister(String username,String password){
        int register = userMapper.register(username, password);
        if (register != 0){
            return new Result(200,"注册成功",null);
        }else {
            return new Result(201,"注册失败",null);
        }
    }

    /*用户名检查*/
    public boolean checkUsername(String username){
        User user = userMapper.findUserName(new User(null, username, null, null));
        return user == null;
    }

}
