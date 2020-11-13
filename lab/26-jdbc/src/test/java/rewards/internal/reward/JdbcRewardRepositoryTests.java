package rewards.internal.reward;

import common.datetime.SimpleDate;
import common.money.MonetaryAmount;
import common.money.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.internal.account.Account;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests the JDBC reward repository with a test data source to verify
 * data access and relational-to-object mapping behavior works as expected.
 *
 * TODO-00: In this lab, you are going to exercise the following:
 * - Refactoring cumbersome low-level JDBC code to leverage Spring's JdbcTemplate
 * - Using various query methods of JdbcTemplate for retrieving data
 * - Implementing callbacks for converting retrieved data into domain object
 *   - RowMapper
 *   - ResultSetExtractor (optional)
 */
public class JdbcRewardRepositoryTests {

	private JdbcRewardRepository repository;

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setUp() throws Exception {
		dataSource = createTestDataSource();
		repository = new JdbcRewardRepository(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Test
	public void testCreateReward() throws SQLException {
		Dining dining = Dining.createDining("100.00", "1234123412341234", "0123456789");

		Account account = new Account("1", "Keith and Keri Donald");
		account.setEntityId(0L);
		account.addBeneficiary("Annabelle", Percentage.valueOf("50%"));
		account.addBeneficiary("Corgan", Percentage.valueOf("50%"));

		AccountContribution contribution = account.makeContribution(MonetaryAmount.valueOf("8.00"));
		RewardConfirmation confirmation = repository.confirmReward(contribution, dining);
		assertNotNull(confirmation, "confirmation should not be null");
		assertNotNull(confirmation.getConfirmationNumber(), "confirmation number should not be null");
		assertEquals(contribution, confirmation.getAccountContribution(), "wrong contribution object");
		verifyRewardInserted(confirmation, dining);
	}

	private void verifyRewardInserted(RewardConfirmation confirmation, Dining dining) throws SQLException {
		assertEquals(1, getRewardCount());

		//	TODO-02: Use JdbcTemplate to query for a map of all column values
		//	of a row in the T_REWARD table based on the confirmationNumber.
		//  - Use "SELECT * FROM T_REWARD WHERE CONFIRMATION_NUMBER = ?" as SQL statement
		//	- After making the changes, execute this test class to verify
		//	  its successful execution.
		//	  (If you are using Gradle, comment out the test exclude in
		//    the build.gradle file.)
		//
		
		Map<String, Object> values = null;
		verifyInsertedValues(confirmation, dining, values);
	}

	private void verifyInsertedValues(RewardConfirmation confirmation, Dining dining, Map<String, Object> values) {
		assertEquals(confirmation.getAccountContribution().getAmount(), new MonetaryAmount((BigDecimal) values
				.get("REWARD_AMOUNT")));
		assertEquals(SimpleDate.today().asDate(), values.get("REWARD_DATE"));
		assertEquals(confirmation.getAccountContribution().getAccountNumber(), values.get("ACCOUNT_NUMBER"));
		assertEquals(dining.getAmount(), new MonetaryAmount((BigDecimal) values.get("DINING_AMOUNT")));
		assertEquals(dining.getMerchantNumber(), values.get("DINING_MERCHANT_NUMBER"));
		assertEquals(SimpleDate.today().asDate(), values.get("DINING_DATE"));
	}

	private int getRewardCount() throws SQLException {
		// TODO-01: Use JdbcTemplate to query for the number of rows in the T_REWARD table
		// - Use "SELECT count(*) FROM T_REWARD" as SQL statement
		return -1;
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder()
			.setName("rewards")
			.addScript("/rewards/testdb/schema.sql")
			.addScript("/rewards/testdb/data.sql")
			.build();
	}
}
