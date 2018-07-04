package rewards.internal;

import java.util.Random;

import org.apache.log4j.Logger;

import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.internal.reward.RewardRepository;

/**
 * A dummy reward repository implementation.
 */
public class StubRewardRepository implements RewardRepository {

	private Logger logger = Logger.getLogger(StubRewardRepository.class);

	/**
	 * Constructor logs creation so we know which repository we are using.
	 */
	public StubRewardRepository() {
		logger.info("Creating " + getClass().getSimpleName());
	}

	public RewardConfirmation confirmReward(AccountContribution contribution, Dining dining) {
		return new RewardConfirmation(confirmationNumber(), contribution);
	}

	private String confirmationNumber() {
		return new Random().toString();
	}
}