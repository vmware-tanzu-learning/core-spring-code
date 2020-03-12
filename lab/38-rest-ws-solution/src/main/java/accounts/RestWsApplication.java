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

    // Start HSQLDB client swing app
//    @PostConstruct
//    public void getDbManager(){
//        DatabaseManagerSwing.main(
//                new String[] { "--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", ""});
//    }
}
