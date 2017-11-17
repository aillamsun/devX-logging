package com.william.logging.core;


import com.william.logging.api.LoggerDefine;
import com.william.logging.core.aop.MethodInterceptorHolder;

import java.lang.reflect.Method;

public interface AccessLoggerParser {
    boolean support(Class clazz, Method method);

    LoggerDefine parse(MethodInterceptorHolder holder);
}
