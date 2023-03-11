package com.cz.huawei_demo.until;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Result {
    private Integer responseCode;
    private String message;
    private Object result;
}
