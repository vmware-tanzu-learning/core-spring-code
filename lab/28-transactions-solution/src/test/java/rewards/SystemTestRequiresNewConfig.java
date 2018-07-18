package rewards;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import rewards.internal.RewardNetworkImplRequiresNew;
import rewards.internal.account.AccountRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.RewardRepository;


@Configuration
@Import(SystemTestConfig.class)
public class SystemTestRequiresNewConfig {

	@Bean
	public RewardNetwork rewardNetwork(
		AccountRepository accountRepository,
		RestaurantRepository restaurantRepository,
		RewardRepository rewardRepository ) {
		return new RewardNetworkImplRequiresNew(
			accountRepository, 
			restaurantRepository, 
			rewardRepository);
	}
	
}
