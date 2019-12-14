package com.hjc.config;

import com.hjc.utils.HJCKeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class CommonConfig {
    @Value("${hjc.workerId}")
    private long workerId = 0;

    @Value("${hjc.datacenterId}")
    private long datacenterId = 0;
    @Bean
    public CommonConfig keyGenerator(){
        HJCKeyGenerator.init(workerId,datacenterId);
        return this;
    }
}
