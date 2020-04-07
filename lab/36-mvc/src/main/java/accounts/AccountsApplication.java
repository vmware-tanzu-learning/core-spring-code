package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import config.AccountsConfig;

/**
 * Spring Boot application.
 *
 * TODO-01: Open the pom.xml or build.gradle for this project and check the dependencies.
 * - Note that we are using Spring Boot starter for Web
 * - Note that we are also using devtools
 *
 * TODO-02: Run the application as a Spring Boot or Java application
 * - Access the home page: http://localhost:8080
 * - Click "List account as JSON" link in the homepage and note that it
 *   won't work - you need to implement it.
 */
@SpringBootApplication
@Import(AccountsConfig.class)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}

/**
 * TODO-12: Make this server listen on port 8088.
 * - Go to application.properties and set the appropriate property
 * - Once the application restarts, access http://localhost:8088
 */
