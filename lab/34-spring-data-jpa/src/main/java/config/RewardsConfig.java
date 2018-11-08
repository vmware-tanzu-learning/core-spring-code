package config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

@Configuration
public class RewardsConfig {

	@Autowired
	DataSource dataSource;
		
	@Bean
	public RewardNetwork rewardNetwork(
		AccountRepository accountRepository, 
		RestaurantRepository restaurantRepository, 
		RewardRepository rewardRepository ) {
		return new RewardNetworkImpl(
			accountRepository, 
			restaurantRepository, 
			rewardRepository);
	}

	@Bean
	public RewardRepository rewardRepository(){
		JdbcRewardRepository repository = new JdbcRewardRepository();
		repository.setDataSource(dataSource);
		return repository;
	}
	
}
