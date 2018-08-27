package config;

import javax.persistence.EntityManager;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import accounts.AccountManager;
import accounts.internal.JpaAccountManager;

/**
 * Sets up the Accounts database only.
 */
@Configuration
@EntityScan("rewards.internal")
@EnableTransactionManagement
public class AccountsConfig implements WebMvcConfigurer {

	/**
	 * A new service has been created for accessing Account information. Internally
	 * it uses JPA directly so no Repository class is required.
	 * 
	 * @param entityManager
	 *            The JPA Entity Manager (actually a proxy).
	 *            <p>
	 *            Spring Boot initializes JPA automatically and Spring's JPA support
	 *            injects a singleton EntityManager proxy by calling
	 *            {@link JpaAccountManager#setEntityManager(EntityManager)}.
	 *            <p>
	 *            At runtime this proxy resolves to the current EntityManager for
	 *            the current transaction of the current thread.
	 * @return The new account-manager instance.
	 */
	@Bean
	public AccountManager accountManager() {
		return new JpaAccountManager();
	}

	/**
	 * Enables the home page which is using server-side rendering of a minimal view.
	 * 
	 * @param registry
	 *            View controller registry. Allows you to register simple mappings
	 *            of URLs to static views (since there is no dynamic content a
	 *            Spring Controller is not required).
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// Map the root URL to the index template
		registry.addViewController("/").setViewName("index");
	}
}
