package com.config;

import com.app.MyOwnHelloService;
import com.lib.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyOwnConfig {

    @Bean
    public HelloService helloService1() {
        return new MyOwnHelloService();
    }
}
