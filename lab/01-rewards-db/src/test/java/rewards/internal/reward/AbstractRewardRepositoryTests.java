package rewards.internal.reward;

import common.money.MonetaryAmount;
import common.money.Percentage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Transactional;
import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.internal.account.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests the JDBC reward repository with a test data source to verify data
 * access and relational-to-object mapping behavior works as expected.
 */
public abstract class AbstractRewardRepositoryTests {

	@Autowired
	protected JdbcRewardRepository rewardRepository;

	@Autowired
	protected DataSource dataSource;

	@Test
	public abstract void testProfile();

	@Test
	@Transactional
	public void createReward() throws SQLException {
		Dining dining = Dining.createDining("100.00", "1234123412341234",
				"0123456789");

		Account account = new Account("1", "Keith and Keri Donald");
		account.setEntityId(0L);
		account.addBeneficiary("Annabelle", Percentage.valueOf("50%"));
		account.addBeneficiary("Corgan", Percentage.valueOf("50%"));

		AccountContribution contribution = account
				.makeContribution(MonetaryAmount.valueOf("8.00"));
		RewardConfirmation confirmation = rewardRepository.confirmReward(
				contribution, dining);
		assertNotNull(confirmation, "confirmation should not be null");
		assertNotNull("confirmation number should not be null",
				confirmation.getConfirmationNumber());
		assertEquals(contribution,
				confirmation.getAccountContribution(), "wrong contribution object");
		verifyRewardInserted(confirmation, dining);
	}

	private void verifyRewardInserted(RewardConfirmation confirmation,
			Dining dining) throws SQLException {
		assertEquals(1, getRewardCount());
		Statement stmt = getCurrentConnection().createStatement();
		ResultSet rs = stmt
				.executeQuery("select REWARD_AMOUNT from T_REWARD where CONFIRMATION_NUMBER = '"
						+ confirmation.getConfirmationNumber() + "'");
		rs.next();
		assertEquals(confirmation.getAccountContribution().getAmount(),
				MonetaryAmount.valueOf(rs.getString(1)));
	}

	private int getRewardCount() throws SQLException {
		Statement stmt = getCurrentConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select count(*) from T_REWARD");
		rs.next();
		return rs.getInt(1);
	}

	/**
	 * Gets the connection behind the current transaction - this allows the
	 * tests to use the transaction created for the @Transactional test and see
	 * the changes made.
	 * <p>
	 * Using a different (new) connection would fail because the T_REWARD table
	 * is locked by any updates and the queries in <tt>verifyRewardInserted</tt>
	 * and <tt>getRewardCount</tt> can never return.
	 * 
	 * @return The current connection
	 */
	private Connection getCurrentConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}
}
