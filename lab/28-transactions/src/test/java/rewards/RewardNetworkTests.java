package rewards;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import common.money.MonetaryAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A system test that verifies the components of the RewardNetwork application
 * work together to reward for dining successfully. Uses Spring to bootstrap the
 * application for use in a test environment.
 *
 * TODO-04: Save all work, run this RewardNetworkTests below.  It should pass.
 *  (If you are using Gradle, remove all exclude statements
 *  from the build.gradle file before running the test.)
 * - Notice that we have enabled DEBUG logging in setup() below.
 * - Check the logging output. Is only ONE connection being used?
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SystemTestConfig.class })
public class RewardNetworkTests {

	/**
	 * The test entry point:
	 */
	@Autowired
	RewardNetwork rewardNetwork;

	@Autowired
	DataSource dataSource;

	JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);

		// Using Logback for logging.
		// Enable DEBUG logging so we can see the transactions
		Logger jdbcLogger = (Logger) LoggerFactory
				.getLogger("org.springframework.jdbc.datasource.DataSourceTransactionManager");
		jdbcLogger.setLevel(Level.DEBUG);

	}

	@Test
	public void testRewardForDining() {
		// create a new dining of 100.00 charged to credit card
		// '1234123412341234' by merchant '123457890' as test input
		Dining dining = Dining.createDining("100.00", "1234123412341234", "1234567890");

		// call the 'rewardNetwork' to test its rewardAccountFor(Dining) method
		RewardConfirmation confirmation = rewardNetwork.rewardAccountFor(dining);

		// Check the DB to see if the Reward is actually on the table:
		String sql = "SELECT COUNT(*) FROM T_REWARD WHERE CONFIRMATION_NUMBER = ? AND REWARD_AMOUNT = ?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, confirmation.getConfirmationNumber(),
				confirmation.getAccountContribution().getAmount().asBigDecimal());

		assertEquals(1, count);

		// assert the expected reward confirmation results
		assertNotNull(confirmation);
		assertNotNull(confirmation.getConfirmationNumber());

		// assert an account contribution was made
		AccountContribution contribution = confirmation.getAccountContribution();
		assertNotNull(contribution);

		// the contribution account number should be '123456789'
		assertEquals("123456789", contribution.getAccountNumber());

		// the total contribution amount should be 8.00 (8% of 100.00)
		assertEquals(MonetaryAmount.valueOf("8.00"), contribution.getAmount());

		// the total contribution amount should have been split into 2
		// distributions
		assertEquals(2, contribution.getDistributions().size());

		// each distribution should be 4.00 (as both have a 50% allocation)
		assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Annabelle").getAmount());
		assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Corgan").getAmount());
	}
}