package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import config.AccountsConfig;

/**
 * Spring Boot application.
 *
 * TODO-01: Open the pom.xml or build.gradle for this project and check the dependencies.
 * In particular we are using the Spring Boot starters for web, jdbc and devtools.
 *
 * TODO-02: Run the application as a Spring Boot or Java application in your
 * IDE.  You should be able to see the home page: http://localhost:8080
 * The "List account as JSON" link in the homepage won't work - you need to
 * implement it.
 *
 * TODO-12: Make this server listen on port 8088.
 *  - Go to application.properties and set the appropriate property
 *  - Once the application restarts, try accessing http://localhost:8088
 */
@SpringBootApplication
@Import(AccountsConfig.class)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
