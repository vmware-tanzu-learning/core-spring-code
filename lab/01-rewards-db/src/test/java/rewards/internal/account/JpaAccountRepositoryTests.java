package rewards.internal.account;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import utils.DataManagementSetup;

/**
 * Manually configured integration test for the JPA based account repository
 * implementation.Tests repository behavior and verifies the Account JPA mapping
 * is correct.
 */
public class JpaAccountRepositoryTests extends AbstractAccountRepositoryTests {

	private PlatformTransactionManager transactionManager;

	private TransactionStatus transactionStatus;

	@Before
	public void setUp() throws Exception {
		DataManagementSetup dataManagementSetup = new DataManagementSetup();
		accountRepository = new JpaAccountRepository(dataManagementSetup.createEntityManager());

		// begin a transaction
		transactionManager = dataManagementSetup.getTransactionManager();
		transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
	}

	@Test
	@Override
	public void testProfile() {
		Assert.assertTrue("JPA expected", accountRepository instanceof JpaAccountRepository);
	}

	@After
	public void tearDown() throws Exception {
		// rollback the transaction to avoid corrupting other tests
		if (transactionManager != null)
			transactionManager.rollback(transactionStatus);
	}
}
