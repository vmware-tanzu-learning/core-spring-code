/**
 * 
 */
package accounts;

import config.MvcConfig;
import config.RootConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;


// TODO-01: Start this Spring Boot application
// - Access the home page at http://localhost:8080/ once it gets started
// - Click on the "View Account List" link, you should reach the list of accounts.
//   You can access the above link because Spring Boot security is temporarily
//   disabled.

// TODO-02: Enable Spring Boot security auto configuration
// - Remove exclude = {SecurityAutoConfiguration.class} below
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// TODO-03: Import the SecurityConfig class - add it to the @Import below.
// - Save all work and let the server restart.
// - You should be able to access the home page, but should see a 404
//   when clicking on "View account list" (we have no login page, yet).
@Import({MvcConfig.class, RootConfig.class})
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

}
