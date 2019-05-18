package auth;

import config.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * OAuth2 Authorization server for authenticating access to the Account server.
 *
 * Normally this would be in a separate project. Because this is in he same
 * project as the Account Server, Spring Boot will pick up its JPA
 * configuration, so all JPA auto-configuration is disabled (we don't need it).
 *
 * TODO-01: Run this application as a Spring Boot or Java application and open
 * http://localhost:1111 in your browser. You should see a login
 * page. The user-name is "user" and the password is in your console output.
 * (If the browser keeps displaying the login page, use Chrome Incognito browser.)
 *
 * TODO-02: If your browser can display JSON, you might like to look at the
 * actuator pages. Note that management.endpoints.web.base-path is set to
 * /admin in the auth-server.properties file.
 *
 * TODO-03: What happens if you access the Superuser Only page? We need to fix
 * that! Open the AuthServerConsoleSecurityConfiguration class.
 *
 * TODO-08: Convert this application into an OAuth2 Server by adding a necessary
 * annotation to this class.
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

	/**
	 * Authorization Server configuration
	 */
	// TODO-09: Configure Authorization server by uncommenting @Bean
	// @Bean
	AuthorizationServerConfigurer authServerConfig() {
		return new AuthorizationServerConfigurerAdapter() {
			/**
			 * Valid clients must be a "trusted client".
			 */
			@Override
			public void configure(AuthorizationServerSecurityConfigurer security) {
				// TODO-10: Check token access - must have trusted client authority
				// You can use ROLE_TRUSTED_CLIENT above.
				security.checkTokenAccess("TODO-10 - replace this string");
			}

			/**
			 * TODO-11: Register Resource server and Client.
			 *
			 * Setup the following configuration:
			 *
			 * To validate a token as the Resource server, you must:
			 * - submit account-server:secret as clientId/password
			 * - have CLIENT_CREDENTIALS as grant type
			 * - have authority ROLE_TRUSTED_CLIENT
			 *
			 * To get an authorization token as a client, you must
			 * - submit account-tester:secret as clientId/password
			 * - have CLIENT_CREDENTIALS as grant type
			 * - have scopes ACCOUNT_READ and ACCOUNT_WRITE
			 *
			 */
			@Override
			public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

				PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

				clients.inMemory() //
						.withClient(Constants.ACCOUNT_SERVER) // Resource Server username
						.secret(passwordEncoder.encode("???"))  // Set encoded password
						.authorizedGrantTypes("???") // = CLIENT_CREDENTIALS
						.authorities("...")          // Has ROLE_TRUSTED_CLIENT
						// Add configuration here
					.and() //
						.withClient(Constants.ACCOUNT_TESTER_CLIENT) // Client username
						// Add configuration here
						;
				
				// TODO-16b: If you configured this properly, the ACCOUNT_TESTER_CLIENT
				//           should have scopes ACCOUNT_READ and ACCOUNT_WRITE.
			}

		};
	}
}
