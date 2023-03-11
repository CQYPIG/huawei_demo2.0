package com.cz.huawei_demo.redis.utils;

public class Lua {
    public static String secKillScript =
            //获取调用secKillScript传递过来的参数，数字标识第几个参数。
            //local类似声明一个变量
            //Object eval = jedis.eval(Lua.secKillScript,2, commodity, userId);调用的时候
                "local userid=KEYS[2];\r\n" +
                "local commodityId=KEYS[1];\r\n" +
                //字符串拼接  lua脚本中的字符串拼接是使用..
                //这里反斜杠以及转义字符得到使用与Java一样
//             "local kcKey=\"sk:\"..prodid..\":qt\";\r\n"+
//             "local userKey=\"sk:\"..prodid..\":user\"\r\n"+
                    "local commodityKCKey=\"COMMODITY:\"..commodityId..\":KC\";\r\n"+
                    "local successUserKey=\"SUCCESS:USERS\";\r\n"+
                    //再lua中调用redis中的命令
                    //第一个参数是需要调用的命令，第二个（第三个）参数是需要的key(value)
                    "local userExists=redis.call(\"sismember\",successUserKey,userid..\":COMMODITY:\"..commodityId);\r\n"+
                    //tonumber() 一个函数，将传递进来的值转化成数字类型
                    "if tonumber(userExists)==1 then \r\n"+
                        //返回2标识重复秒杀
                    "    return 2;\r\n"+
                    //语句结束还需要一个end？？？666
                    //if的基础语法if() then
                    "end\r\n"+
                    "local num=redis.call(\"get\", commodityKCKey);\r\n"+
                    "if tonumber(num)<=0 then \r\n"+
                        //0标识还没开始或者已经结束
                    "    return 0;"+
                    "else "+
                    "    redis.call(\"decr\",commodityKCKey);\r\n"+
                    "    redis.call(\"sadd\", successUserKey, userid..\":COMMODITY:\"..commodityId);\r\n"+
                    "end\r\n"+
                    "return 1";
}