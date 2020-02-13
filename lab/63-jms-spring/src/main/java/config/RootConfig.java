package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.RewardRepository;

@Configuration
@Import({AppConfig.class,DbConfig.class})
@Profile("jpa")
@EnableTransactionManagement
public class RootConfig {
	
	/**
	 * Given references to the *Repository classes, create a RewardNetwork:
	 */
	@Bean public RewardNetwork rewardNetwork(
			AccountRepository accountRepository, 
			RestaurantRepository restaurantRepository, 
			RewardRepository rewardRepository){
		return new RewardNetworkImpl(
			accountRepository, 
			restaurantRepository, 
			rewardRepository);
	}
}
