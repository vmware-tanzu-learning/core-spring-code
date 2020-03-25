package rewards.internal.account;

import common.money.MonetaryAmount;
import common.money.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the JDBC account repository with a test data source to verify data access and relational-to-object mapping
 * behavior works as expected.
 */
public class JdbcAccountRepositoryTests {

	private JdbcAccountRepository repository;

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setUp() throws Exception {
		dataSource = createTestDataSource();
		repository = new JdbcAccountRepository(createTestJdbcTemplate());
	}

	@Test
	public void testFindAccountByCreditCard() {
		Account account = repository.findByCreditCard("1234123412341234");
		// assert the returned account contains what you expect given the state of the database
		assertNotNull(account, "account should never be null");
		assertEquals(Long.valueOf(0), account.getEntityId(), "wrong entity id");
		assertEquals("123456789", account.getNumber(), "wrong account number");
		assertEquals("Keith and Keri Donald", account.getName(), "wrong name");
		assertEquals(2, account.getBeneficiaries().size(), "wrong beneficiary collection size");

		Beneficiary b1 = account.getBeneficiary("Annabelle");
		assertNotNull(b1, "Annabelle should be a beneficiary");
		assertEquals(MonetaryAmount.valueOf("0.00"), b1.getSavings(), "wrong savings");
		assertEquals(Percentage.valueOf("50%"), b1.getAllocationPercentage(), "wrong allocation percentage");

		Beneficiary b2 = account.getBeneficiary("Corgan");
		assertNotNull(b2, "Corgan should be a beneficiary");
		assertEquals(MonetaryAmount.valueOf("0.00"), b2.getSavings(), "wrong savings");
		assertEquals(Percentage.valueOf("50%"), b2.getAllocationPercentage(), "wrong allocation percentage");
	}

	@Test
	public void testFindAccountByCreditCardNoAccount() {
		assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.findByCreditCard("bogus");
		});
	}

	@Test
	public void testUpdateBeneficiaries() throws SQLException {
		Account account = repository.findByCreditCard("1234123412341234");
		account.makeContribution(MonetaryAmount.valueOf("8.00"));
		repository.updateBeneficiaries(account);
		verifyBeneficiaryTableUpdated();
	}

	private void verifyBeneficiaryTableUpdated() throws SQLException {
		String sql = "select SAVINGS from T_ACCOUNT_BENEFICIARY where NAME = ? and ACCOUNT_ID = ?";
		PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql);

		// assert Annabelle has $4.00 savings now
		stmt.setString(1, "Annabelle");
		stmt.setLong(2, 0L);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		assertEquals(MonetaryAmount.valueOf("4.00"), MonetaryAmount.valueOf(rs.getString(1)));

		// assert Corgan has $4.00 savings now
		stmt.setString(1, "Corgan");
		stmt.setLong(2, 0L);
		rs = stmt.executeQuery();
		rs.next();
		assertEquals(MonetaryAmount.valueOf("4.00"), MonetaryAmount.valueOf(rs.getString(1)));
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
