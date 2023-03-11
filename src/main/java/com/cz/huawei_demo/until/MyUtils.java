package com.cz.huawei_demo.until;


import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MyUtils {
    //这里要求传入的字符串满足特定的格式 key:value,key:value
    public String HandleString(String str){
        String[] split = str.split(",");
        for (String value : split) {
            String[] split1 = value.split(":");
            for (int j = 0; j < split1.length; j++) {
                //key为颜色的时候输出他的value
                if ("颜色".equals(split1[j])) {
                    return split1[j + 1];
                }
            }
        }
        return null;
    }

    public String getFormatTime(){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        return time;
    }


    public static boolean hasUrl(String requestUrl){
        if(requestUrl.contains("order") || requestUrl.contains("Order")){
            return true;
        }else if(requestUrl.contains("shopCar") || requestUrl.contains("ShopCar")){
            return true;
        }else if(requestUrl.contains("checkToken")){
            return true;
        }

        return false;
    }
}
