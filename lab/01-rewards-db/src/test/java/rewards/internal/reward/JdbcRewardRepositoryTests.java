package rewards.internal.reward;

import javax.sql.DataSource;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

/**
 * Tests the JDBC reward repository with a test data source to test repository
 * behavior and verifies the Reward JDBC code is correct.
 */
public class JdbcRewardRepositoryTests extends AbstractRewardRepositoryTests {

	@Before
	public void setUp() throws Exception {
		dataSource = createTestDataSource();
		rewardRepository = new JdbcRewardRepository(dataSource);
	}

	@Test
	@Override
	public void testProfile() {
		Assert.assertTrue("JDBC expected",
				rewardRepository instanceof JdbcRewardRepository);
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder().setName("rewards")
				.addScript("/rewards/testdb/schema.sql")
				.addScript("/rewards/testdb/data.sql").build();
	}
}
