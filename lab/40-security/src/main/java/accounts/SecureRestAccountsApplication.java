package accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import config.AppConfig;
import config.DbConfig;

/**
 * The Account Server is now also a Resource Server so access to Account
 * information is restricted by OAuth2.
 */
// TODO-15: Annotate to make this an OAuth2 resource-server
@SpringBootApplication
@Import({ AppConfig.class, DbConfig.class })
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
	// TODO-16: Make Spring create this bean - uncomment @Bean.
	//          Note below that GET access requires account.read scope and
	//          POST access requires account.write scope.
	//          Scopes are a form of Authority (an alternative to a Role).
	//
	// TODO-17: Review the Client properties in account-server.properties.
	//
	// TODO-18: Try to access http://localhost:8080/accounts. You should get
	//          a 401 error.
	
	//@Bean
	ResourceServerConfigurer resourceServerConfigurer() {
		return new ResourceServerConfigurer() {
			@Override
			public void configure(ResourceServerSecurityConfigurer resources) {
				// This is the security "Realm"
				resources.resourceId("accounts");
			}

			/**
			 * Secure all accounts pages.
			 */
			@Override
			public void configure(HttpSecurity http) throws Exception {
				http.authorizeRequests() //
						.mvcMatchers(HttpMethod.GET, "/accounts/**") //
						.access("#oauth2.hasScope('account.read')") //
						.mvcMatchers(HttpMethod.POST, "/accounts/**") //
						.access("#oauth2.hasScope('account.write')");
			}
		};
	}
}
