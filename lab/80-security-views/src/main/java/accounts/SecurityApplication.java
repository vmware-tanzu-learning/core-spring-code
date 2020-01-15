/**
 * 
 */
package accounts;

import config.MvcConfig;
import config.RootConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


// TODO-01: Start this Spring Boot application

// TODO-02: Access the home page at http://localhost:8080/ once it gets started
// - Click on the "View Account List" link, you should reach the list of accounts.

@SpringBootApplication
// TODO-03: Import the SecurityConfig class - add it to the @Import below.
// - Save all work and let the server restart.
// - You should be able to access the home page, but should see a 404
//   when clicking on "View account list" (we have no login page, yet).
@Import({MvcConfig.class,RootConfig.class})
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

}
