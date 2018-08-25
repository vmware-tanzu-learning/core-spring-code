package rewards.internal.restaurant;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import utils.DataManagementSetup;

/**
 * Manually configured integration test for the JPA based restaurant repository
 * implementation. Tests repository behavior and verifies the Restaurant JPA
 * mapping is correct.
 */
public class JpaRestaurantRepositoryTests extends AbstractRestaurantRepositoryTests {

	private PlatformTransactionManager transactionManager;

	private TransactionStatus transactionStatus;

	@Before
	public void setUp() throws Exception {
		DataManagementSetup dataManagementSetup = new DataManagementSetup();

		JpaRestaurantRepository restaurantRepository = new JpaRestaurantRepository();
		restaurantRepository.setEntityManager(dataManagementSetup.createEntityManager());
		this.restaurantRepository = restaurantRepository;

		// begin a transaction
		transactionManager = dataManagementSetup.getTransactionManager();
		transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
	}

	@Test
	@Override
	public void testProfile() {
		Assert.assertTrue("JPA expected", restaurantRepository instanceof JpaRestaurantRepository);
	}

	@After
	public void tearDown() throws Exception {
		// rollback the transaction to avoid corrupting other tests
		if (transactionManager != null)
			transactionManager.rollback(transactionStatus);
	}

}
