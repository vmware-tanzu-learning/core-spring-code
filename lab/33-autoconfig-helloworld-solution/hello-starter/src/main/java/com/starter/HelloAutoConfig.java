package com.starter;

import com.lib.HelloService;
import com.lib.TypicalHelloService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(HelloService.class)
public class HelloAutoConfig {

    @ConditionalOnMissingBean(HelloService.class)
    @Bean
    HelloService helloService() {
        return new TypicalHelloService();
    }

}
