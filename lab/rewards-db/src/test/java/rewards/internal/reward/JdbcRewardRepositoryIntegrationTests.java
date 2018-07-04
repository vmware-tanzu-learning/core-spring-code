package rewards.internal.reward;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.AppConfig;
import config.DbConfig;

/**
 * Integration test for the JDBC-based rewards repository implementation.
 * Verifies that the JdbcRewardRepository works with its underlying components
 * and that Spring is configuring things properly.
 */
@ActiveProfiles("jpa")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class,DbConfig.class})
public class JdbcRewardRepositoryIntegrationTests extends
		AbstractRewardRepositoryTests {

	@Test
	@Override
	public void testProfile() {
		Assert.assertTrue(
				"JDBC expected but found " + rewardRepository.getInfo(),
				rewardRepository.getInfo().equals(JdbcRewardRepository.TYPE));
	}

}
