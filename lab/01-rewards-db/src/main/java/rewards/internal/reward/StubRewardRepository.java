package rewards.internal.reward;

import java.util.Random;

import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;

/**
 * A dummy reward repository implementation.
 *
 * IMPORTANT!!! Per best practices, this class shouldn't be in 'src/main/java'
 * but rather in 'src/test/java'. However, it is used by numerous Test classes
 * inside multiple projects. Maven does not provide an easy way to access a
 * class that is inside another project's 'src/test/java' folder.
 * 
 * Rather than using some complex Maven configuration, we decided it is
 * acceptable to place this test class inside 'src/main/java'.
 */
public class StubRewardRepository implements RewardRepository {

	public static final String TYPE = "Stub";

	int nextConfirmationNumber = 0;

	@Override
	public RewardConfirmation confirmReward(AccountContribution contribution, Dining dining) {
		return new RewardConfirmation(confirmationNumber(), contribution);
	}

	@Override
	public String getInfo() {
		return TYPE;
	}

	private String confirmationNumber() {
		return String.valueOf(nextConfirmationNumber++);
	}
}