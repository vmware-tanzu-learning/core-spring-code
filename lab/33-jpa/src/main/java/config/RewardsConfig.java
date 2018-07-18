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

//	TODO-06: Add an annotation that will scan for your new JPA repositories. 
//	Note that this configuration class is not in the same base package as the
//	repository components you would have created in the previous steps.

@Configuration
@EnableTransactionManagement
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
