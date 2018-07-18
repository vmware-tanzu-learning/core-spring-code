package config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import javax.sql.DataSource;

import org.assertj.core.api.Fail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;
import rewards.internal.restaurant.JdbcRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

/**
 * Unit test the Spring configuration class to ensure it is creating the right
 * beans.
 */
public class RewardsConfigTests {
	// Provide a mock for testing
	private DataSource dataSource = Mockito.mock(DataSource.class);

	private RewardsConfig rewardsConfig = new RewardsConfig(dataSource);

	@Test
	public void getBeans() {
		RewardNetwork rewardNetwork = rewardsConfig.rewardNetwork();
		assertTrue(rewardNetwork instanceof RewardNetworkImpl);

		AccountRepository accountRepository = rewardsConfig.accountRepository();
		assertTrue(accountRepository instanceof JdbcAccountRepository);
		checkDataSource(accountRepository);

		RestaurantRepository restaurantRepository = rewardsConfig.restaurantRepository();
		assertTrue(restaurantRepository instanceof JdbcRestaurantRepository);
		checkDataSource(restaurantRepository);

		RewardRepository rewardsRepository = rewardsConfig.rewardRepository();
		assertTrue(rewardsRepository instanceof JdbcRewardRepository);
		checkDataSource(rewardsRepository);
	}

	/**
	 * Ensure the data-source is set for the repository. Uses reflection as we do
	 * not wish to provide a getDataSource() method.
	 * 
	 * @param repository
	 */
	private void checkDataSource(Object repository) {
		Class<? extends Object> repositoryClass = repository.getClass();

		try {
			Field dataSource = repositoryClass.getDeclaredField("dataSource");
			dataSource.setAccessible(true);
			assertNotNull(dataSource.get(repository));
		} catch (Exception e) {
			String failureMessage = "Unable to validate dataSource in " + repositoryClass.getSimpleName();
			System.out.println(failureMessage);
			e.printStackTrace();
			Fail.fail(failureMessage);
		}
	}
}
