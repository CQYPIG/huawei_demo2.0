package com.cz.huawei_demo.servlet;


import com.alibaba.fastjson2.JSON;
import com.cz.huawei_demo.until.MyUtils;
import com.cz.huawei_demo.until.Result;
import com.cz.huawei_demo.until.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 过滤器过滤未登录状态发起的请求
 * 我是测试
 * @author yingfeng
 * @date 2022/10/27 18:04
 */

@Slf4j
@PropertySource(value = {"classpath:redis.properties"})
public class LoginStateFilter implements Filter {


    @Value("${host}")
    private String host;
    @Value("${password}")
    private String password;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("过滤器启动");
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Result result = new Result();
        HttpSession session = httpRequest.getSession();
        //判断当前请求路径是否需要过滤
        String requestURI = httpRequest.getRequestURI();

//        需要过滤的
        if (MyUtils.hasUrl(requestURI)){
            String token = httpRequest.getHeader("token");
            if(token != null && !"".equals(token)){
                //处理token,去掉首尾的引号
                token = token.substring(1,token.length()-1);
                //连接redis,从redis中获取token
                JedisPool jedisPool = new JedisPool("124.222.201.199",6379);
                Jedis jedis = jedisPool.getResource();
                jedis.auth("cz1234");
                String attribute = jedis.get(token);
                jedis.close();
                log.info("取出的token:"+attribute);
                if(attribute == null || "".equals(attribute)){
                    log.info(requestURI+"路径token过期");
                    try {
                        result.setResponseCode(442);
                        returnJson(servletResponse,result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            //没有token,第一次登录
            }else {
                result.setResponseCode(4440);
                result.setMessage("第一次登录");
                try {
                    returnJson(servletResponse,result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
    private void returnJson(ServletResponse response, Object result) throws Exception{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        //将封装过的result对象转成json字符串
        String jsonResult = JSON.toJSONString(result);

//        httpServletResponse.setStatus(408);

        try (PrintWriter writer = response.getWriter()) {
            //返回json字符串
            writer.print(jsonResult);
        } catch (IOException e) {
            log.error("response error", e);
        }
    }


    @Override
    public void destroy() {
        log.info("过滤器销毁");

    }
}
