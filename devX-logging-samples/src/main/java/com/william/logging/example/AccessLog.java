package com.william.logging.example;

import com.alibaba.fastjson.JSON;
import com.william.logging.api.AccessLoggerInfo;
import com.william.logging.api.AccessLoggerListener;
import org.springframework.stereotype.Component;

/**
 * Created by sungang on 2017/11/17.
 */
@Component
public class AccessLog implements AccessLoggerListener {
    @Override
    public void onLogger(AccessLoggerInfo loggerInfo) {
        System.out.println(JSON.toJSONString(loggerInfo));
    }
}
