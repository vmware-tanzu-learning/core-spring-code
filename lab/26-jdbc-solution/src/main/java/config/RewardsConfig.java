package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;
import rewards.internal.restaurant.JdbcRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

import javax.sql.DataSource;

@Configuration
public class RewardsConfig {

	JdbcTemplate jdbcTemplate;

	@Autowired  // Optional, Spring will autowire DataSource anyway
	public RewardsConfig(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Bean
	public RewardNetwork rewardNetwork(){
		return new RewardNetworkImpl(
			accountRepository(), 
			restaurantRepository(), 
			rewardRepository());
	}
	
	@Bean
	public AccountRepository accountRepository(){
		JdbcAccountRepository repository = new JdbcAccountRepository(jdbcTemplate);
		return repository;
	}
	
	@Bean
	public RestaurantRepository restaurantRepository(){
		JdbcRestaurantRepository repository = new JdbcRestaurantRepository(jdbcTemplate);
		return repository;
	}
	
	@Bean
	public RewardRepository rewardRepository(){
		JdbcRewardRepository repository = new JdbcRewardRepository(jdbcTemplate);
		return repository;
	}
	
}
