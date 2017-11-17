package com.william.logging.core;


import com.william.logging.api.AccessLogger;
import com.william.logging.api.LoggerDefine;
import com.william.logging.core.aop.MethodInterceptorHolder;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;


public class DefaultAccessLoggerParser implements AccessLoggerParser {
    @Override
    public boolean support(Class clazz, Method method) {
        AccessLogger ann = AnnotationUtils.findAnnotation(method, AccessLogger.class);
        //注解了并且未取消
        return null != ann && !ann.ignore();
    }

    @Override
    public LoggerDefine parse(MethodInterceptorHolder holder) {
        AccessLogger methodAnn = holder.findMethodAnnotation(AccessLogger.class);
        AccessLogger classAnn = holder.findClassAnnotation(AccessLogger.class);
        String action = Stream.of(classAnn, methodAnn)
                .filter(Objects::nonNull)
                .map(AccessLogger::module)
                .reduce((c, m) -> c.concat("-").concat(m))
                .orElse("");
        String describe = Stream.of(classAnn, methodAnn)
                .filter(Objects::nonNull)
                .map(AccessLogger::describe)
                .flatMap(Stream::of)
                .reduce((c, s) -> c.concat("\n").concat(s))
                .orElse("");

        String type = "";
        if (null != methodAnn){
            type = methodAnn.type().getValue();
        }
        return new LoggerDefine(action,describe,type);
    }
}
