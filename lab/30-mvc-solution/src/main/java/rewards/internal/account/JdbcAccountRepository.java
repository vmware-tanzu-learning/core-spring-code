package rewards.internal.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import common.money.MonetaryAmount;
import common.money.Percentage;

/**
 * Loads accounts from a data source using the JDBC API.
 */
public class JdbcAccountRepository implements AccountRepository {

	private static String ACCOUNTS_QUERY = //
			"SELECT a.ID as ID, a.NUMBER as ACCOUNT_NUMBER, a.NAME as ACCOUNT_NAME,"
					+ " c.NUMBER as CREDIT_CARD_NUMBER,"
					+ " b.NAME as BENEFICIARY_NAME, b.ALLOCATION_PERCENTAGE as BENEFICIARY_ALLOCATION_PERCENTAGE,"
					+ " b.SAVINGS as BENEFICIARY_SAVINGS "
					+ "FROM T_ACCOUNT a INNER JOIN T_ACCOUNT_CREDIT_CARD c ON ID = c.ACCOUNT_ID"
					+ " LEFT JOIN T_ACCOUNT_BENEFICIARY b ON ID = b.ACCOUNT_ID";

	private JdbcTemplate jdbcTemplate;

	/**
	 * Extracts a List of Account objects from rows returned from a join of
	 * T_ACCOUNT and T_ACCOUNT_BENEFICIARY.
	 */
	private ResultSetExtractor<List<Account>> accountsListExtractor = new AccountsListExtractor();

	/**
	 * Extracts an Account object from rows returned from a join of T_ACCOUNT and
	 * T_ACCOUNT_BENEFICIARY.
	 */
	private ResultSetExtractor<Account> accountExtractor = new AccountExtractor();

	public JdbcAccountRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Account> getAllAccounts() {
		String sql = ACCOUNTS_QUERY;
		return jdbcTemplate.query(sql, accountsListExtractor);
	}

	/**
	 * {@inheritDoc}
	 */
	public Account getAccount(Long id) {
		String sql = ACCOUNTS_QUERY + " WHERE a.ID = ?";
		return jdbcTemplate.query(sql, accountExtractor, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public Account findByCreditCard(String creditCardNumber) {
		String sql = ACCOUNTS_QUERY + " WHERE c.NUMBER = ?";
		return jdbcTemplate.query(sql, accountExtractor, creditCardNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateBeneficiaries(Account account) {
		String sql = "update T_ACCOUNT_BENEFICIARY SET SAVINGS = ? where ACCOUNT_ID = ? and NAME = ?";
		for (Beneficiary b : account.getBeneficiaries()) {
			jdbcTemplate.update(sql, b.getSavings().asBigDecimal(), account.getEntityId(), b.getName());
		}
	}

	/**
	 * Map the rows returned from the join of T_ACCOUNT and T_ACCOUNT_BENEFICIARY to
	 * a list of fully-reconstituted Account (with Beneficiaries) aggregates.
	 * 
	 * @param rs
	 *            The set of rows returned from the query
	 * @return the mapped Account aggregate
	 * @throws SQLException
	 *             An exception occurred extracting data from the result set
	 */
	private List<Account> mapAccounts(ResultSet rs) throws SQLException {
		List<Account> accounts = new ArrayList<>();
		long previous = -1;
		Account account = null;

		while (rs.next()) {
			long entityId = rs.getLong("ID");

			if (previous != entityId) {
				String number = rs.getString("ACCOUNT_NUMBER");
				String name = rs.getString("ACCOUNT_NAME");
				account = new Account(number, name);
				accounts.add(account);

				// Set internal entity identifier (primary key)
				account.setEntityId(rs.getLong("ID"));
				previous = entityId;
			}

			Beneficiary beneficiary = mapBeneficiary(rs);
			if (beneficiary != null)
				account.restoreBeneficiary(beneficiary);
		}

		return accounts;
	}

	/**
	 * Map the rows returned from the join of T_ACCOUNT and T_ACCOUNT_BENEFICIARY to
	 * an fully-reconstituted Account aggregate.
	 * 
	 * @param rs
	 *            the set of rows returned from the query
	 * @return the mapped Account aggregate
	 * @throws SQLException
	 *             an exception occurred extracting data from the result set
	 */
	private Account mapAccount(ResultSet rs) throws SQLException {
		List<Account> accounts = mapAccounts(rs);

		if (accounts.size() == 0) {
			// Only one row expected - throw an exception
			System.out.println(" >> NO ACCOUNTS FOUND");
			throw new EmptyResultDataAccessException(1);
		}

		if (accounts.size() > 1) {
			// Only one row expected - throw an exception
			System.out.println(" >> FOUND " + accounts.size() + " accounts");
			System.out.println(" >>    >> " + accounts);
			throw new IncorrectResultSizeDataAccessException(1);
		}

		return accounts.get(0);
	}

	/**
	 * Maps the beneficiary columns in a single row to an AllocatedBeneficiary
	 * object.
	 * 
	 * @param rs
	 *            the result set with its cursor positioned at the current row
	 * @return an allocated beneficiary
	 * @throws SQLException
	 *             an exception occurred extracting data from the result set
	 */
	private Beneficiary mapBeneficiary(ResultSet rs) throws SQLException {
		String name = rs.getString("BENEFICIARY_NAME");

		if (name == null)
			return null;

		MonetaryAmount savings = MonetaryAmount.valueOf(rs.getString("BENEFICIARY_SAVINGS"));
		Percentage allocationPercentage = Percentage.valueOf(rs.getString("BENEFICIARY_ALLOCATION_PERCENTAGE"));
		return new Beneficiary(name, allocationPercentage, savings);
	}

	private class AccountsListExtractor implements ResultSetExtractor<List<Account>> {

		public List<Account> extractData(ResultSet rs) throws SQLException, DataAccessException {
			return mapAccounts(rs);
		}

	}

	private class AccountExtractor implements ResultSetExtractor<Account> {

		public Account extractData(ResultSet rs) throws SQLException, DataAccessException {
			return mapAccount(rs);
		}

	}

}
