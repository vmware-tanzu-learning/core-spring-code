package rewards;

import common.money.MonetaryAmount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rewards.CaptureSystemOutput.OutputCapture;
import rewards.internal.aspects.LoggingAspect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A system test that verifies the components of the RewardNetwork application
 * work together to reward for dining successfully. Uses Spring to bootstrap the
 * application for use in a test environment.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={SystemTestConfig.class})
@EnableAutoConfiguration
public class RewardNetworkTests {

	/**
	 * The object being tested.
	 */
	@Autowired
	private RewardNetwork rewardNetwork;

	@Test
	@CaptureSystemOutput
	public void testRewardForDining(OutputCapture capture) {
		// create a new dining of 100.00 charged to credit card '1234123412341234' by merchant '123457890' as test input
		Dining dining = Dining.createDining("100.00", "1234123412341234", "1234567890");

		// call the 'rewardNetwork' to test its rewardAccountFor(Dining) method
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
		
		// TODO-06: Run this test. It should pass AND you should see TWO lines of
		// log output from the LoggingAspect on the console
		int expectedMatches = 2;
		checkConsoleOutput(capture, expectedMatches);
		
		// TODO-09: Save all your work, and change the expected matches value above from 2 to 4.
		// Rerun the RewardNetworkTests.  It should pass, and you should now see FOUR lines of
		// console output from the LoggingAspect.
	}
	
    /**
     * Not only must the code run, but the LoggingAspect should generate logging
     * output to the console.
     */
    private void checkConsoleOutput(OutputCapture capture, int expectedMatches) {
        // Don't run these checks until we are ready
        if (!TestConstants.CHECK_CONSOLE_OUTPUT)
            return;
        
        // AOP VERIFICATION
        // Expecting 4 lines of output from the LoggingAspect to console
        String[] consoleOutput = capture.toString().split("\n");
        int matches = 0;
        
        for (String line : consoleOutput) {
            if (line.contains("rewards.internal.aspects.LoggingAspect")) {
                if (line.contains(LoggingAspect.BEFORE)) {
                    if (line.contains("JdbcAccountRepository") && line.contains("findByCreditCard"))
                        // Before aspect invoked for
                        // JdbcAccountRepository.findByCreditCard
                        matches++;
                    else if (line.contains("JdbcRestaurantRepository") && line.contains("findByMerchantNumber"))
                        // Before aspect invoked for
                        // JdbcRestaurantRepository.findByMerchantNumber
                        matches++;
                } else if (line.contains(LoggingAspect.AROUND)) {
                    if (line.contains("AccountRepository") && line.contains("updateBeneficiaries"))
                        // Around aspect invoked for
                        // AccountRepository.updateBeneficiaries
                        matches++;
                    else if (line.contains("Around") && line.contains("RewardRepository")
                             && line.contains("updateReward"))
                        // Around aspect invoked for
                        // RewardRepository.updateReward
                        matches++;
                }
            }
        }
        
        assertEquals(expectedMatches, matches);
    }

}