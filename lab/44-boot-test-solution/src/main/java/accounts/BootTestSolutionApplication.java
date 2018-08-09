package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import config.AppConfig;
import config.DbConfig;

@SpringBootApplication
@Import({AppConfig.class,DbConfig.class})
public class BootTestSolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootTestSolutionApplication.class, args);
    }

}
