package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import accounts.AccountManager;
import accounts.internal.JpaAccountManager;

/**
 * Imports Rewards application from rewards-db project.
 */
@Configuration
@Import({ AppConfig.class, DbConfig.class })
@EnableTransactionManagement
public class RootConfig {

	/**
	 * A service for accessing Account information.
	 * 
	 * @return The new account-manager instance.
	 */
	@Bean
	public AccountManager accountManager() {
		return new JpaAccountManager();
	}

}
