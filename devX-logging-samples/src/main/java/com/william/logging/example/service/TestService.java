package com.william.logging.example.service;

import com.william.logging.api.AccessLogger;
import com.william.logging.api.enums.SysLogType;
import org.springframework.stereotype.Service;

/**
 * Created by sungang on 2018/1/9.
 */
@Service
public class TestService {



    @AccessLogger(module = "测试Service", describe = "测试Service", type = SysLogType.AECCESS)
    public String testService() {
        return "AECCESS";
    }
}
