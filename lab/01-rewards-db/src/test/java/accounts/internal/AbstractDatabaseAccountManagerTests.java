package accounts.internal;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import utils.TransactionUtils;

import javax.sql.DataSource;

/**
 * Supports transactional testing of AccountManager implementation in both a
 * manual and a Spring-configured environment.
 * <p>
 * Manual configuration, allows testing of an AccountManager implementation
 * without Spring.
 * <p>
 * Automated configuration using a class annotated with @ContextConfiguration
 * tests both the implementation of AccountManager and the Spring configuration
 * files.
 */
public abstract class AbstractDatabaseAccountManagerTests extends AbstractAccountManagerTests {

	protected static int numAccountsInDb = -1;

	@Autowired
	protected PlatformTransactionManager transactionManager;

	@Autowired
	protected DataSource dataSource;

	protected TransactionUtils transactionUtils;

	@Override
	protected void showStatus() {
		logger.info("TRANSACTION IS : " + transactionUtils.getCurrentTransaction());
	}

	@BeforeEach
	public void setUp() throws Exception {
		// The number of test accounts in the database - a static variable so we only do
		// this once.
		if (numAccountsInDb == -1)
			numAccountsInDb = new JdbcTemplate(dataSource).queryForObject("SELECT count(*) FROM T_Account",
					Integer.class);

		// Setup the transaction utility class
		transactionUtils = new TransactionUtils(transactionManager);
	}

	@Override
	protected int getNumAccountsExpected() {
		return numAccountsInDb;
	}
}
