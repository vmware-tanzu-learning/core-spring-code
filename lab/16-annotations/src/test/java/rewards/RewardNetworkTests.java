package rewards;

import common.money.MonetaryAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A system test that verifies the components of the RewardNetwork
 * application work together to reward for dining successfully.
 * It uses Spring to bootstrap the application for use in a test environment.
 * 
 * TODO-00: In this lab, you are going to exercise the following:
 * - Refactoring the current code that uses Spring configuration with
 *   @Bean methods so that it uses annotation and component-scanning instead
 * - Using constructor injection and setter injection
 * - Using @PostConstruct and @PreDestroy
 *
 * TODO-01: Run this test before making any changes.
 * - It should pass.
 *   Note that this test passes only when all the required
 *   beans are correctly configured.
 */
public class RewardNetworkTests {

	/**
	 * The object being tested.
	 */
	private RewardNetwork rewardNetwork;

	@BeforeEach
	public void setUp() {
		// Create application context from TestInfrastructureConfig,
		// which also imports RewardsConfig
		ApplicationContext context = SpringApplication.run(TestInfrastructureConfig.class);
		
		// Get rewardNetwork bean from the application context
		rewardNetwork = context.getBean(RewardNetwork.class);
	}

	@Test
	public void testRewardForDining() {
		// create a new dining of 100.00 charged to credit card '1234123412341234' by merchant '123457890' as test input
		Dining dining = Dining.createDining("100.00", "1234123412341234", "1234567890");

		// call the 'rewardNetwork' to test its rewardAccountFor(Dining) method
		// this fails if you have selected an account without beneficiaries!
		RewardConfirmation confirmation = rewardNetwork.rewardAccountFor(dining);

		// assert the expected reward confirmation results
		assertNotNull(confirmation);
		assertNotNull(confirmation.getConfirmationNumber());

		// assert an account contribution was made
		AccountContribution contribution = confirmation.getAccountContribution();
		assertNotNull(contribution);

		// the contribution account number should be '123456789'
		assertEquals("123456789", contribution.getAccountNumber());

		// the total contribution amount should be 8.00 (8% of 100.00)
		assertEquals(MonetaryAmount.valueOf("8.00"), contribution.getAmount());

		// the total contribution amount should have been split into 2 distributions
		assertEquals(2, contribution.getDistributions().size());

		// each distribution should be 4.00 (as both have a 50% allocation)
		assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Annabelle").getAmount());
		assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Corgan").getAmount());
	}
}
