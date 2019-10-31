package rewards.internal.account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import utils.DataManagementSetup;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Manually configured integration test for the JPA based account repository
 * implementation.Tests repository behavior and verifies the Account JPA mapping
 * is correct.
 */
public class JpaAccountRepositoryTests extends AbstractAccountRepositoryTests {

	private PlatformTransactionManager transactionManager;

	private TransactionStatus transactionStatus;

	@BeforeEach
	public void setUp() throws Exception {
		DataManagementSetup dataManagementSetup = new DataManagementSetup();

		JpaAccountRepository accountRepository = new JpaAccountRepository();
		accountRepository.setEntityManager(dataManagementSetup.createEntityManager());
		this.accountRepository = accountRepository;

		// begin a transaction
		transactionManager = dataManagementSetup.getTransactionManager();
		transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
	}

	@Test
	@Override
	public void testProfile() {
		assertTrue(accountRepository instanceof JpaAccountRepository, "JPA expected");
	}

	@AfterEach
	public void tearDown() throws Exception {
		// rollback the transaction to avoid corrupting other tests
		if (transactionManager != null)
			transactionManager.rollback(transactionStatus);
	}
}
