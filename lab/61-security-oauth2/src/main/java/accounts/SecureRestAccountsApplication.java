package accounts;

import config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * The Account Server is now also a Resource Server so access to Account
 * information is restricted by OAuth2.
 *
 * TODO-08: Annotate this class to make it an OAuth2 resource-server
 *
 * TODO-10: Run this application
 * - Try to access http://localhost:8080/accounts. You should get `unauthorized`
 * 	 error response.
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
	 * Configure Resource Server.
	 */

	// TODO-09c: Configure Resource server by uncommenting @Bean.
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
			 * AuthorizationServer.
			 */
			@Override
			public void configure(HttpSecurity http) throws Exception {
				// TODO-09a: Review the access restrictions.
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
