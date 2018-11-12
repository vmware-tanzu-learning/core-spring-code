package auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import config.Constants;

/**
 * OAuth2 Authorization server for authenticating access to the Account server.
 * <p>
 * Normally this would be in a separate project. Because this is in he same
 * project as the Account Server, Spring Boot will pick up its JPA
 * configuration, so all JPA auto-configuration is disabled (we don't need it).
 * <p>
 * TODO-01: Run this application as a Spring Boot or Java application and open
 * http://locahost:1111 in your browser. Login and you should see the console
 * page. The user-name is "user" and the password is in your console output.
 * <p>
 * TODO-02: If your browser can display JSON, you might like to look at the
 * actuator pages. Note that management.endpoints.web.base-path is set to
 * /admin in the auth-server.properties file.
 * <p>
 * TODO-03: What happens if you access the Superuser Only page? We need to fix
 * that! Open the {@link AuthServerConsoleSecurityConfiguration}.
 * <p>
 * TODO-08: Now to convert this process into an OAuth2 Server. Add the necessary
 * annotation to this class
 */
@SpringBootApplication
@SuppressWarnings("deprecation")
@EnableAutoConfiguration(exclude = { JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
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

	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	/**
	 * Authorization Server configuration
	 * 
	 * @return
	 */
	// TODO-09: Make this bean active - uncomment @Bean
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
			 * TODO-11: Configure access credentials.
			 * <p>
			 * The two clients you need to setup have been started for you.
			 * <p>
			 * Setup the following configuration:
			 * <ul>
			 * <li>To access as the Resource Server you must:<br/>
			 * - submit account-server:secret as clientId/password<br/>
			 * - have {@link CLIENT_CREDENTIALS} as grant<br/>
			 * - have authority {@link ROLE_TRUSTED_CLIENT}<br/>
			 * <p>
			 * <li>To get an authorization token you must<br/>
			 * - submit account-tester:secret as clientId/password<br/>
			 * - have {@link CLIENT_CREDENTIALS} as grant<br/>
			 * - have scopes {@link ACCOUNT_READ} and {@link ACCOUNT_WRITE}
			 * </ul>
			 */
			@Override
			public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

				clients.inMemory() //
						.withClient(Constants.ACCOUNT_SERVER) // Resource Server username
						.secret("???")               // Set password
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
