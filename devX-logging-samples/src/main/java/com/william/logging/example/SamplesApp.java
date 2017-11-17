package com.william.logging.example;

import com.william.logging.core.EnableAccessLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

/**
 * Created by su
 * ngang on 2017/11/17.
 */
@SpringBootApplication
@EnableAccessLogger
public class SamplesApp extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SamplesApp.class);
    }

    /**
     * spring boot 服务主入口
     *
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SamplesApp.class, args);
    }
}
