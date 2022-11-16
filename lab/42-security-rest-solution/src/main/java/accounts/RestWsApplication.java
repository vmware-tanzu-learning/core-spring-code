package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

import config.RestSecurityConfig;

@SpringBootApplication
@Import(RestSecurityConfig.class)
@EntityScan("rewards.internal")
public class RestWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWsApplication.class, args);
    }

}
