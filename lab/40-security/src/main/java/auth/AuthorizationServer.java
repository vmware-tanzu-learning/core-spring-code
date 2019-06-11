package auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * OAuth2 Authorization server for authenticating access to the Account server.
 *
 * Normally this would be in a separate project. Because this is in he same
 * project as the Account Server, Spring Boot will pick up its JPA
 * configuration, so all JPA auto-configuration is disabled (we don't need it).
 *
 * TODO-01: Run this application and open http://localhost:1111 in your browser.
 * You should see a login page.
 * The username is "user" and the password is in your console output.
 * (If the browser keeps displaying the login page, use Chrome Incognito browser.)
 *
 * TODO-02: Access "Recent requests" and "health" pages.
 *
 * TODO-03: What happens if you access the Superuser Only page?
 * You can access this page as a regular user.  This page is not protected.
 * We will address that. Open the AuthServerConsoleSecurityConfiguration class.
 */
@SpringBootApplication(exclude = { JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class AuthorizationServer {

	public static final String CLIENT_CREDENTIALS = "client_credentials";
	public static final String ROLE_TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";

	public static final String ACCOUNT_READ = "account.read";
	public static final String ACCOUNT_WRITE = "account.write";

	public static void main(String[] args) {
		// This is the name of the properties file to read. Instead of
		// "application.properties" it will read "account-server.properties".
		System.setProperty("spring.config.name", "auth-server");

		SpringApplication.run(AuthorizationServer.class, args);
	}

}
