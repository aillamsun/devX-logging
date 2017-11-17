package com.william.logging.core;

import com.william.logging.api.AccessLoggerListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AOP 访问日志记录自动配置
 * Created by sungang on 2017/11/17.
 */
@ConditionalOnClass(AccessLoggerListener.class)
@Configuration
public class AopAccessLoggerSupportAutoConfiguration {

    @Bean
    public AopAccessLoggerSupport aopAccessLoggerSupport() {
        return new AopAccessLoggerSupport();
    }

    @Bean
    public DefaultAccessLoggerParser defaultAccessLoggerParser(){
        return new DefaultAccessLoggerParser();
    }

    @Bean
    public ListenerProcessor listenerProcessor() {
        return new ListenerProcessor();
    }

    public static class ListenerProcessor implements BeanPostProcessor {

        @Autowired
        private AopAccessLoggerSupport aopAccessLoggerSupport;

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof AccessLoggerListener) {
                aopAccessLoggerSupport.addListener(((AccessLoggerListener) bean));
            }  if (bean instanceof AccessLoggerParser) {
                aopAccessLoggerSupport.addParser(((AccessLoggerParser) bean));
            }
            return bean;
        }
    }

}
