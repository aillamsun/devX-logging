package com.william.logging.example.controller;

import com.william.logging.api.AccessLogger;
import com.william.logging.api.enums.SysLogType;
import com.william.logging.example.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sungang on 2017/11/16.
 */
@RestController
@RequestMapping("log")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(method = RequestMethod.POST)
    @AccessLogger(module = "测试日志模块", describe = "测试登录", type = SysLogType.LOGIN)
    public String testLogin() {
        return "LOGIN";
    }

    @RequestMapping(method = RequestMethod.GET)
    @AccessLogger(module = "测试日志模块", describe = "测试访问", type = SysLogType.AECCESS)
    public String testAccess() {
        return "AECCESS";
    }


    @RequestMapping(method = RequestMethod.PUT)
    @AccessLogger(module = "测试日志模块", describe = "测试操作", type = SysLogType.OPER)
    public String testOper() {
        return "OPER";
    }


    @RequestMapping(value = "no", method = RequestMethod.PUT)
    public String testNo() {
        return "OPER";
    }

    @RequestMapping(value = "service", method = RequestMethod.GET)
    public String testService() {
        return testService.testService();
    }
}
