package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());

	DataSource dataSource;

	@Autowired  // Optional, Spring will call this constructor anyway
	public RewardsConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

    // TODO-09 : Switch back to explicit `DataSource` configuration
    //           - Uncomment @Bean method below
    //           - Remove the code above that performs DataSource injection
    //           - Fix compile errors
    /*
    @Bean
    public DataSource dataSource() {
        logger.debug("Creating the datasource bean explicitly");

        return
                (new EmbeddedDatabaseBuilder())
                        .addScript("classpath:schema.sql")
                        .addScript("classpath:data.sql")
                        .build();
    }
    */

    @Bean
    public RewardNetwork rewardNetwork() {
        return new RewardNetworkImpl(
                accountRepository(),
                restaurantRepository(),
                rewardRepository());
    }

    @Bean
    public AccountRepository accountRepository() {
        JdbcAccountRepository repository = new JdbcAccountRepository(dataSource);
        return repository;
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        JdbcRestaurantRepository repository = new JdbcRestaurantRepository(dataSource);
        return repository;
    }

    @Bean
    public RewardRepository rewardRepository() {
        JdbcRewardRepository repository = new JdbcRewardRepository(dataSource);
        return repository;
    }

}
