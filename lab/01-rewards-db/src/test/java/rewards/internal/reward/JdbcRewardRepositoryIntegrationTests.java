package rewards.internal.reward;


import config.AppConfig;
import config.DbConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Integration test for the JDBC-based rewards repository implementation.
 * Verifies that the JdbcRewardRepository works with its underlying components
 * and that Spring is configuring things properly.
 */
@ActiveProfiles("jpa")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={AppConfig.class,DbConfig.class})
public class JdbcRewardRepositoryIntegrationTests extends
		AbstractRewardRepositoryTests {

	@Test
	@Override
	public void testProfile() {
		assertTrue(
				rewardRepository.getInfo().equals(JdbcRewardRepository.TYPE),
				"JDBC expected but found " + rewardRepository.getInfo());
	}

}
