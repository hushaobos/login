package com.hjc.config;

//import io.seata.spring.annotation.GlobalTransactionScanner;
import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class SeataConfig {
    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
        return new GlobalTransactionScanner("${spring.application.name}", "hjc_tx_group");
    }
}
