package com.app;

import com.config.MyOwnConfig;
import com.lib.HelloService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({MyOwnConfig.class})
public class HelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner( HelloService helloService) {

		return args -> helloService.greet();

	}

}
