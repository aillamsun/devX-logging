package com.william.logging.core;

import com.william.logging.api.AccessLoggerListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
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
    @ConditionalOnMissingBean(AopAccessLoggerSupport.class)
    public AopAccessLoggerSupport aopAccessLoggerSupport() {
        return new AopAccessLoggerSupport();
    }

    @Bean
    @ConditionalOnMissingBean(AccessLoggerParser.class)
    public AccessLoggerParser defaultAccessLoggerParser() {
        return new DefaultAccessLoggerParser();
    }

    @Bean
    @ConditionalOnMissingBean(ListenerProcessor.class)
    public ListenerProcessor listenerProcessor(AopAccessLoggerSupport aopAccessLoggerSupport) {
        return new ListenerProcessor(aopAccessLoggerSupport);
    }

    public static class ListenerProcessor implements BeanPostProcessor {

        private final AopAccessLoggerSupport aopAccessLoggerSupport;

        public ListenerProcessor(AopAccessLoggerSupport aopAccessLoggerSupport) {
            this.aopAccessLoggerSupport = aopAccessLoggerSupport;
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof AccessLoggerListener) {
                aopAccessLoggerSupport.addListener(((AccessLoggerListener) bean));
            }
            if (bean instanceof AccessLoggerParser) {
                aopAccessLoggerSupport.addParser(((AccessLoggerParser) bean));
            }
            return bean;
        }
    }

}
