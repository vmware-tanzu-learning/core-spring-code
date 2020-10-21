package rewards.internal;

import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.RewardNetwork;
import rewards.internal.account.AccountRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.RewardRepository;

/**
 * Rewards an Account for Dining at a Restaurant.
 * 
 * The sole Reward Network implementation. This object is an application-layer service responsible for coordinating with
 * the domain-layer to carry out the process of rewarding benefits to accounts for dining.
 * 
 * Said in other words, this class implements the "reward account for dining" use case.
 *
 * TODO-00: In this lab, you are going to exercise the following:
 * - Understanding internal operations that need to be performed to implement
 *   "rewardAccountFor" method of the "RewardNetworkImpl" class
 * - Writing test code using stub implementations of dependencies
 * - Writing both target code and test code without using Spring framework
 *
 * TODO-01: Review the Rewards Application document (Refer to the lab document)
 * TODO-02: Review project dependencies (Refer to the lab document)
 * TODO-03: Review Rewards Commons project (Refer to the lab document)
 * TODO-04: Review RewardNetwork interface and RewardNetworkImpl class below
 * TODO-05: Review the RewardNetworkImpl configuration logic (Refer to the lab document)
 * TODO-06: Review sequence diagram (Refer to the lab document)
 */
public class RewardNetworkImpl implements RewardNetwork {

	private AccountRepository accountRepository;

	private RestaurantRepository restaurantRepository;

	private RewardRepository rewardRepository;

	/**
	 * Creates a new reward network.
	 * @param accountRepository the repository for loading accounts to reward
	 * @param restaurantRepository the repository for loading restaurants that determine how much to reward
	 * @param rewardRepository the repository for recording a record of successful reward transactions
	 */
	public RewardNetworkImpl(AccountRepository accountRepository, RestaurantRepository restaurantRepository,
			RewardRepository rewardRepository) {
		this.accountRepository = accountRepository;
		this.restaurantRepository = restaurantRepository;
		this.rewardRepository = rewardRepository;
	}

	public RewardConfirmation rewardAccountFor(Dining dining) {
		// TODO-07: Write code here for rewarding an account according to
		//          the sequence diagram in the lab document
		// TODO-08: Return the corresponding reward confirmation
		return null;
	}
}