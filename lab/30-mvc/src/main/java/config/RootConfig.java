package config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import accounts.AccountManager;
import accounts.AccountManagerImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;

/**
 * Imports Rewards application from rewards-db project.
 */
@Configuration
@Import(DbConfig.class)
@EntityScan("rewards")
@EnableTransactionManagement
public class RootConfig implements WebMvcConfigurer {

	/**
	 * A new service has been created for accessing Account information.
	 * 
	 * @return The new account-manager instance.
	 */
	@Bean
	public AccountManager accountManager(AccountRepository accountRepository) {
		return new AccountManagerImpl(accountRepository);
	}

	/**
	 * Repository for accessing Account information. This is an extended
	 * implementation of the one used in the JDBC Template lab.
	 * 
	 * @return The new account-repository instance.
	 */
	@Bean
	public AccountRepository accountRepository(DataSource dataSource) {
		return new JdbcAccountRepository(dataSource);
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
