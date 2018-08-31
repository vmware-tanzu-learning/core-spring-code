package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import auth.AuthorizationServer;
import config.AppConfig;
import config.DbConfig;

/**
 * The Account Server is now also a Resource Server so access to Account
 * information is restricted by OAuth2.
 * <p>
 * TODO-15: Annotate this class to make it an OAuth2 resource-server
 * <p>
 * TODO-17: Run this class as a Spring Boot application.
 * - Try to access http://localhost:8080/accounts. You should get an access
 *   restricted or denied response
 */
@SpringBootApplication
@Import(AppConfig.class)
@EntityScan("rewards.internal")
public class SecureRestAccountsApplication {

	public static void main(String[] args) {
		// This is the name of the properties file to read. Instead of
		// application.properties it will read account-server.properties.
		System.setProperty("spring.config.name", "account-server");

		SpringApplication.run(SecureRestAccountsApplication.class, args);
	}

	/**
	 * Configure security using OAuth2 now we are a Resource Server.
	 * 
	 * @return Configuration as a Resource Server.
	 */

	// TODO-16c: Make Spring create this bean - uncomment @Bean.
	// @Bean
	ResourceServerConfigurer resourceServerConfigurer() {
		return new ResourceServerConfigurer() {
			@Override
			public void configure(ResourceServerSecurityConfigurer resources) {
				// This is the security "Realm"
				resources.resourceId("accounts");
			}

			/**
			 * Secure all accounts pages. We are restricting by "scope" - scope is just
			 * another type of Authority (like roles). These match the configuration in the
			 * {@link AuthorizationServer}.
			 */
			@Override
			public void configure(HttpSecurity http) throws Exception {
				// TODO-16a: Review the access restrictions.
				// - GET access requires account.read scope and
				// - POST access requires account.write scope.
				// - Scopes are a form of Authority (an alternative to a Role).

				http.authorizeRequests() //
						.mvcMatchers(HttpMethod.GET, "/accounts/**") //
						.access("#oauth2.hasScope('account.read')") //
						.mvcMatchers(HttpMethod.POST, "/accounts/**") //
						.access("#oauth2.hasScope('account.write')");
			}
		};
	}
}
