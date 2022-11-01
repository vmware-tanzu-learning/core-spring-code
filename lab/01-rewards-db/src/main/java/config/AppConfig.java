package config;

import accounts.AccountManager;
import accounts.internal.JpaAccountManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JpaAccountRepository;
import rewards.internal.restaurant.JpaRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

import javax.sql.DataSource;

/**
 * Rewards application configuration - services and repositories.
 * <p>
 * Because this is used by many similar lab projects with slightly different
 * classes and packages, everything is explicitly created using @Bean methods.
 * Component-scanning risks picking up unwanted beans in the same package in
 * other projects.
 */
@Configuration
public class AppConfig {

	@Bean
	public AccountManager accountManager() {
		return new JpaAccountManager();
	}

	@Bean
	public AccountRepository accountRepository() {
		return new JpaAccountRepository();
	}

	@Bean
	public RestaurantRepository restaurantRepository() {
		return new JpaRestaurantRepository();
	}

	@Bean
	public RewardRepository rewardRepository(DataSource dataSource) {
		return new JdbcRewardRepository(dataSource);
	}

}
