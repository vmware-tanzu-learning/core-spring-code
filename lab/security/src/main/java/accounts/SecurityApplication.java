/**
 * 
 */
package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import config.MvcConfig;
import config.RootConfig;
import config.SecurityConfig;


/**
 * <p>
 * TODO-01: Launch this Spring Boot application by doing right-click -> Run As -> Spring Boot App
 * Access the home page at http://localhost:8080/.
 * <p>
 * Click on the "View Account List" link, you should reach the list of accounts.
 * If the application is not working, please ask your instructor for help
 */

// TODO-02: Let Spring Boot enable Spring Security for you. This is done
//          by removing "exclude=SecurityAutoConfiguration.class" below!
@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
@Configuration
@ComponentScan
// TODO-03: Import the SecurityConfig class - add it to the @Import below.
//          Save all work and let the server restart.
//          You should be able to access the home page, but should see a 404
//          when clicking on "View account list" (we have no login page, yet).
@Import({MvcConfig.class,RootConfig.class})
public class SecurityApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

}
