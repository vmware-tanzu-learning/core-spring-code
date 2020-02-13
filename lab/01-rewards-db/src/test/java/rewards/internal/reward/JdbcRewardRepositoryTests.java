package rewards.internal.reward;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the JDBC reward repository with a test data source to test repository
 * behavior and verifies the Reward JDBC code is correct.
 */
public class JdbcRewardRepositoryTests extends AbstractRewardRepositoryTests {

	@BeforeEach
	public void setUp() throws Exception {
		dataSource = createTestDataSource();
		rewardRepository = new JdbcRewardRepository(dataSource);
	}

	@Test
	@Override
	public void testProfile() {
		assertTrue(
				rewardRepository instanceof JdbcRewardRepository, "JDBC expected");
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder().setName("rewards")
				.addScript("/rewards/testdb/schema.sql")
				.addScript("/rewards/testdb/data.sql").build();
	}
}
