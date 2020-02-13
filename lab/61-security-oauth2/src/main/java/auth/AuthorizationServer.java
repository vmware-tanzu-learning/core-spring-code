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
 * TODO-01: Convert this application into an OAuth2 Server by adding a necessary
 * annotation to this class.
 */
@SpringBootApplication(
		exclude = {JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
		scanBasePackages = "config")
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
	// TODO-02: Configure Authorization server by uncommenting @Bean
	// @Bean
	AuthorizationServerConfigurer authServerConfig() {
		return new AuthorizationServerConfigurerAdapter() {
			/**
			 * Valid clients must be a "trusted client".
			 */
			@Override
			public void configure(AuthorizationServerSecurityConfigurer security) {
				// TODO-03: Check token access - must have trusted client authority
				// You can use ROLE_TRUSTED_CLIENT above.
				security.checkTokenAccess("replace this string");
			}

			/**
			 * TODO-04: Register Resource server and Client
			 *          to the Authorization Server and start
			 *          the Authorization server.
			 *
			 * Setup the following configuration and then
			 * start this server application.
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
						// Update configuration here
						.withClient(Constants.ACCOUNT_SERVER) // Resource Server username
						.secret(passwordEncoder.encode("???"))  // Set encoded password
						.authorizedGrantTypes("???") // = CLIENT_CREDENTIALS
						.authorities("...")          // Has ROLE_TRUSTED_CLIENT
					.and() //
						.withClient(Constants.ACCOUNT_TESTER_CLIENT) // Client username
						// Add configuration here
						;
				
				// TODO-09b: If you configured this properly, the ACCOUNT_TESTER_CLIENT
				//           should have scopes ACCOUNT_READ and ACCOUNT_WRITE.
			}

		};
	}
}
