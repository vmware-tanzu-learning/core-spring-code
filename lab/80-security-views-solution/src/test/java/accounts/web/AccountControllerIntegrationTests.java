package accounts.web;

import accounts.internal.JpaAccountManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * A JUnit test case testing the AccountController. Inherits and runs all the
 * tests in {@link AbstractAccountControllerTests} using the rewards in-memory
 * test-database and a {@link JpaAccountManager}.
 */
@Transactional
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)  // not needed since @SpringBootTest finds it
public class AccountControllerIntegrationTests extends
		AbstractAccountControllerTests {

	@Autowired
	DataSource dataSource;

	private static int numAccountsInDb = -1;

	@BeforeEach
	public void setUp() {
		// The number of test accounts in the database
		// - a static variable so we only do this one.
		if (numAccountsInDb == -1)
			numAccountsInDb = new JdbcTemplate(dataSource)
					.queryForObject("SELECT count(*) FROM T_Account", Integer.class); 
	}

	@Override
	protected int getNumAccountsExpected() {
		return numAccountsInDb;
	}

}
