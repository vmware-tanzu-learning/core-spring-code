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
 * Imports Rewards application from rewards-db project.
 */
@Configuration
@EntityScan("rewards.internal")
@EnableTransactionManagement
public class RootConfig implements WebMvcConfigurer {

	/**
	 * A new service has been created for accessing Account information. Internally
	 * it uses JPA directly so no Respository class is required.
	 * 
	 * @param entityaAnager
	 *            The JPA Entity Manager (actually a proxy).
	 *            <p>
	 *            Spring Boot initializes JPA automatically and Spring creates a
	 *            singleton EntityManager proxy for injection. At runtime this proxy
	 *            resolves to the current EntityManager for the current transaction
	 *            of the current thread.
	 * @return The new account-manager instance.
	 */
	@Bean
	public AccountManager accountManager(EntityManager entityaAnager) {
		return new JpaAccountManager(entityaAnager);
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
