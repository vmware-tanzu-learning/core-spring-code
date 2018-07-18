package accounts.internal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import utils.DataManagementSetup;

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
		Assert.assertTrue("JPA expected", accountManager instanceof JpaAccountManager);
		logger.info("JPA with Hibernate");
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		transactionUtils.beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		transactionUtils.rollbackTransaction();
	}

	private void setupForTest() {
		dataSource = dataManagementSetup.getDataSource();

		accountManager = new JpaAccountManager(dataManagementSetup.createEntityManager());
		transactionManager = dataManagementSetup.getTransactionManager();
	}

}
