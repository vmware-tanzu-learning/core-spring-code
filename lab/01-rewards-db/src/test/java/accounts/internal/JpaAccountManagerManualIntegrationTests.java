package accounts.internal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DataManagementSetup;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Manually configured integration test (not using Spring) for the JPA-based
 * account manager implementation. Verifies that the JpaAccountManager works
 * with its underlying components.
 */
public class JpaAccountManagerManualIntegrationTests extends AbstractDatabaseAccountManagerTests {

	DataManagementSetup dataManagementSetup = new DataManagementSetup();

	public JpaAccountManagerManualIntegrationTests() {
		setupForTest();
	}

	@Test
	@Override
	public void testProfile() {
		assertTrue(accountManager instanceof JpaAccountManager, "JPA expected");
		logger.info("JPA with Hibernate");
	}

	@BeforeEach
	@Override
	public void setUp() throws Exception {
		super.setUp();
		transactionUtils.beginTransaction();
	}

	@AfterEach
	public void tearDown() throws Exception {
		transactionUtils.rollbackTransaction();
	}

	private void setupForTest() {
		dataSource = dataManagementSetup.getDataSource();

		JpaAccountManager accountManager = new JpaAccountManager();
		accountManager.setEntityManager(dataManagementSetup.createEntityManager());
		this.accountManager = accountManager;
		transactionManager = dataManagementSetup.getTransactionManager();
	}

}
