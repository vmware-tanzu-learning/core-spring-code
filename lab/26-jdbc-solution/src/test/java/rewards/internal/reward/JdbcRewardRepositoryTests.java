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
 * Tests the JDBC reward repository with a test data source to verify data access and relational-to-object mapping
 * behavior works as expected.
 */
public class JdbcRewardRepositoryTests {

	private JdbcRewardRepository repository;

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setUp() throws Exception {
		dataSource = createTestDataSource();
		jdbcTemplate = createTestJdbcTemplate();
		repository = new JdbcRewardRepository(jdbcTemplate);

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
		String sql = "select * from T_REWARD where CONFIRMATION_NUMBER = ?";
		Map<String, Object> values = jdbcTemplate.queryForMap(sql, confirmation.getConfirmationNumber());
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
		String sql = "select count(*) from T_REWARD";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder()
			.setName("rewards")
			.addScript("/rewards/testdb/schema.sql")
			.addScript("/rewards/testdb/data.sql")
			.build();
	}

	private JdbcTemplate createTestJdbcTemplate() {
		return new JdbcTemplate(createTestDataSource());
	}
}
