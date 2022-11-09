package accounts;

import config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

/**
 * Runs the Account Server.
 */
@SpringBootApplication
public class BootTestSolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootTestSolutionApplication.class, args);
	}

}
