package rewards.internal;

import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.internal.reward.RewardRepository;

import java.util.Random;

/**
 * A dummy reward repository implementation.
 */
public class StubRewardRepository implements RewardRepository {

	public RewardConfirmation confirmReward(AccountContribution contribution, Dining dining) {
		return new RewardConfirmation(confirmationNumber(), contribution);
	}

	private String confirmationNumber() {
		return new Random().toString();
	}
}