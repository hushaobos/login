package com.hjc.config;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.container.Main;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;

/**
 * @author 胡绍波
 */
//@EnableDubbo
//@EnableDubboConfiguration
//@DubboComponentScan("com.hjc")
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = "com.hjc")
@ComponentScan(basePackages="com.hjc")
@Async
@EnableAutoConfiguration
public class LoginApplication extends SpringBootServletInitializer {
    static Logger logger = LoggerFactory.getLogger(LoginApplication.class);


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LoginApplication.class);
    }

    public static void main(String[] args)
    {
        logger.debug(" dddddde ");
        SpringApplication.run(LoginApplication.class,args);
    }
}
