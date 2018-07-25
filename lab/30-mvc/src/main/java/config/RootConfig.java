package config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import accounts.AccountManager;
import accounts.internal.JpaAccountManager;

/**
 * Imports Rewards application from rewards-db project.
 */
@Configuration
@Import({ AppConfig.class, DbConfig.class })
@EntityScan("rewards")
@EnableTransactionManagement
public class RootConfig implements WebMvcConfigurer {

	/**
	 * A new service has been created for accessing Account information.
	 * 
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
	 *            View controller registry. Allows a simple mapping of a URL to a
	 *            view.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// Map the root URL to the index template
		registry.addViewController("/").setViewName("index");
	}
}
