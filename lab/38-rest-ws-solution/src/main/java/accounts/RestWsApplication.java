package accounts;

import config.AppConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
@EntityScan("rewards.internal")
public class RestWsApplication {

    public static void main(String[] args) {

        // SpringApplication.run(RestWsApplication.class, args);
        new SpringApplicationBuilder(RestWsApplication.class)
                .headless(false)
                .run(args);

    }
}
