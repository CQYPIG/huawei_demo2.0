package com.cz.huawei_demo.dao;

import com.cz.huawei_demo.entity.Commodity;
import com.cz.huawei_demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /*判断用户名是否存在*/
    @Select("select user_name from user where user_name = #{userName}")
    User findUserName(User user);

    /*用户登录*/
    @Select("select * from user where user_name = #{userName} and password = #{password}")
    User login(User user);

    /*用户注册*/
    @Insert("insert into user(user_name, password) value (#{username},#{password})")
    int register(@Param("username")String username,@Param("password")String password);

}
