package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
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
//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class RewardsConfig {
	private final Logger logger
			= LoggerFactory.getLogger(RewardsConfig.class);

//	@Autowired private DataSource dataSource;
//
//	private DataSource dataSource() {
//		return this.dataSource;
//	}

	@Bean
	public DataSource dataSource() {
		logger.debug("Creating the datasource bean explicitly");

		return
				(new EmbeddedDatabaseBuilder())
						.addScript("classpath:/schema.sql")
						.addScript("classpath:/data.sql")
						.build();
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
		JdbcAccountRepository repository = new JdbcAccountRepository(dataSource());
		return repository;
	}

	@Bean
	public RestaurantRepository restaurantRepository(){
		JdbcRestaurantRepository repository = new JdbcRestaurantRepository(dataSource());
		return repository;
	}

	@Bean
	public RewardRepository rewardRepository(){
		JdbcRewardRepository repository = new JdbcRewardRepository(dataSource());
		return repository;
	}

}
