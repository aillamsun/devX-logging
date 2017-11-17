package com.william.logging.core;

import com.william.logging.api.AccessLogger;
import com.william.logging.api.AccessLoggerInfo;
import com.william.logging.api.AccessLoggerListener;
import com.william.logging.api.LoggerDefine;
import com.william.logging.api.enums.SysLogType;
import com.william.logging.core.aop.MethodInterceptorHolder;
import com.william.logging.core.utils.AopUtils;
import com.william.logging.core.utils.IDGenerator;
import com.william.logging.core.utils.WebUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用AOP记录访问日志,并触发{@link AccessLoggerListener#onLogger(AccessLoggerInfo)}
 *
 * @author zhouhao
 * @since 3.0
 */
public class AopAccessLoggerSupport extends StaticMethodMatcherPointcutAdvisor {

    private final List<AccessLoggerListener> listeners = new ArrayList<>();

    private final List<AccessLoggerParser> loggerParsers = new ArrayList<>();

    public AopAccessLoggerSupport addListener(AccessLoggerListener loggerListener) {
        listeners.add(loggerListener);
        return this;
    }

    public AopAccessLoggerSupport addParser(AccessLoggerParser parser) {
        loggerParsers.add(parser);
        return this;
    }

    public AopAccessLoggerSupport() {
        setAdvice((MethodInterceptor) methodInvocation -> {
            MethodInterceptorHolder methodInterceptorHolder = MethodInterceptorHolder.create(methodInvocation);
            AccessLoggerInfo info = createLogger(methodInterceptorHolder);
            Object response;
            try {
                listeners.forEach(listener -> listener.onLogBefore(info));
                response = methodInvocation.proceed();
                info.setResponse(response);
                info.setResponseTime(System.currentTimeMillis());
            } catch (Throwable e) {
                info.setException(e);
                info.setAction("异常");
                throw e;
            } finally {
                //触发监听
                listeners.forEach(listener -> listener.onLogger(info));
            }
            return response;
        });
    }

    protected AccessLoggerInfo createLogger(MethodInterceptorHolder holder) {
        AccessLoggerInfo info = new AccessLoggerInfo();
        info.setId(IDGenerator.MD5.generate());

        info.setRequestTime(System.currentTimeMillis());


        LoggerDefine define = loggerParsers.stream()
                .filter(parser -> parser.support(ClassUtils.getUserClass(holder.getTarget()), holder.getMethod()))
                .findAny()
                .map(parser -> parser.parse(holder))
                .orElse(null);

        if (define != null) {
            info.setModule(define.getModule());
            info.setDescribe(define.getDescribe());
            String type = define.getType();
            if (SysLogType.AECCESS.getValue().equals(type)) {
                info.setAction("访问");
            } else if (SysLogType.LOGIN.getValue().equals(type)) {
                info.setAction("登录");
            } else if (SysLogType.OPER.getValue().equals(type)) {
                info.setAction("操作");
            }
        }
        info.setParameters(holder.getArgs());
        info.setTarget(holder.getTarget().getClass());
        info.setMethod(holder.getMethod());

        HttpServletRequest request = WebUtil.getHttpServletRequest();
        if (null != request) {
            info.setHttpHeaders(WebUtil.getHeaders(request));
            info.setIp(WebUtil.getIpAddr(request));
            info.setHttpMethod(request.getMethod());
            info.setUrl(request.getRequestURL().toString());
        }
        return info;

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        AccessLogger ann = AopUtils.findAnnotation(aClass, method, AccessLogger.class);
        if (ann != null && ann.ignore()) {
            return false;
        }
        RequestMapping mapping = AopUtils.findAnnotation(aClass, method, RequestMapping.class);
        return mapping != null;

//        //注解了并且未取消
//        return null != ann && !ann.ignore();
    }
}
