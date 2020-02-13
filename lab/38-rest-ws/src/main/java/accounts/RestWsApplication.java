package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

import config.AppConfig;

@SpringBootApplication
@Import(AppConfig.class)
@EntityScan("rewards.internal")
public class RestWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWsApplication.class, args);
    }
    
	// TODO-01: Run this Spring Boot application
	// IMPORTANT: Make sure that you are not still running an application
	// from a previous lab.
	// Verify you can reach http://localhost:8080 from a browser.
    
}
