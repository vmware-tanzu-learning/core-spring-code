package rewards.internal.reward;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.internal.account.Account;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import common.money.MonetaryAmount;
import common.money.Percentage;

/**
 * Tests the JDBC reward repository with a test data source to verify data access and relational-to-object mapping
 * behavior works as expected.
 */
public class JdbcRewardRepositoryTests {

	private JdbcRewardRepository repository;

	private DataSource dataSource;

	@BeforeEach
	public void setUp() throws Exception {
		repository = new JdbcRewardRepository();
		dataSource = createTestDataSource();
		repository.setDataSource(dataSource);
	}

	@Test
	public void testCreateReward() throws SQLException {
		Dining dining = Dining.createDining("100.00", "1234123412341234", "0123456789");

		Account account = new Account("1", "Keith and Keri Donald");
		account.addBeneficiary("Annabelle", Percentage.valueOf("50%"));
		account.addBeneficiary("Corgan", Percentage.valueOf("50%"));

		AccountContribution contribution = account.makeContribution(MonetaryAmount.valueOf("8.00"));
		RewardConfirmation confirmation = repository.confirmReward(contribution, dining);
		assertNotNull(confirmation, "confirmation should not be null");
		assertNotNull("confirmation number should not be null", confirmation.getConfirmationNumber());
		assertEquals(contribution, confirmation.getAccountContribution(), "wrong contribution object");
		verifyRewardInserted(confirmation, dining);
	}

	private void verifyRewardInserted(RewardConfirmation confirmation, Dining dining) throws SQLException {
		assertEquals(1, getRewardCount());
		Statement stmt = dataSource.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select REWARD_AMOUNT from T_REWARD where CONFIRMATION_NUMBER = '"
				+ confirmation.getConfirmationNumber() + "'");
		rs.next();
		assertEquals(confirmation.getAccountContribution().getAmount(), MonetaryAmount.valueOf(rs.getString(1)));
	}

	private int getRewardCount() throws SQLException {
		Statement stmt = dataSource.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select count(*) from T_REWARD");
		rs.next();
		return rs.getInt(1);
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder()
			.setName("rewards")
			.addScript("/rewards/testdb/schema.sql")
			.addScript("/rewards/testdb/data.sql")
			.build();
	}
}
