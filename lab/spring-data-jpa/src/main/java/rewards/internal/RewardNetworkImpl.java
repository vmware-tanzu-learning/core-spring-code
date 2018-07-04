package rewards.internal;

import org.springframework.transaction.annotation.Transactional;

import rewards.AccountContribution;
import rewards.Dining;
import rewards.RewardConfirmation;
import rewards.RewardNetwork;
import rewards.internal.account.Account;
import rewards.internal.account.AccountRepository;
import rewards.internal.restaurant.Restaurant;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.RewardRepository;

import common.money.MonetaryAmount;

/**
 * Rewards an Account for Dining at a Restaurant.
 * <p>
 * The sole Reward Network implementation. This object is an application-layer
 * service responsible for coordinating with the domain-layer to carry out the
 * process of rewarding benefits to accounts for dining.
 * <p>
 * Said in other words, this class implements the "reward account for dining"
 * use case/story.
 */
public class RewardNetworkImpl implements RewardNetwork {

	private AccountRepository accountRepository;

	private RestaurantRepository restaurantRepository;

	private RewardRepository rewardRepository;

	/**
	 * Creates a new reward network.
	 * 
	 * @param accountRepository
	 *            the repository for loading accounts to reward
	 * @param restaurantRepository
	 *            the repository for loading restaurants that determine how much to
	 *            reward
	 * @param rewardRepository
	 *            the repository for recording a record of successful reward
	 *            transactions
	 */
	public RewardNetworkImpl(AccountRepository accountRepository, RestaurantRepository restaurantRepository,
			RewardRepository rewardRepository) {
		this.accountRepository = accountRepository;
		this.restaurantRepository = restaurantRepository;
		this.rewardRepository = rewardRepository;
	}

	// TODO-06: Check that the code below is using the new method names on
	// the accountRepository and restaurantRepository.
	//
	// This code should look familiar; it is the same code you implemented in the
	// first lab!
	@Transactional
	public RewardConfirmation rewardAccountFor(Dining dining) {
		Account account = accountRepository.findByCreditCard(dining.getCreditCardNumber());
		Restaurant restaurant = restaurantRepository.findByMerchantNumber(dining.getMerchantNumber());
		MonetaryAmount amount = restaurant.calculateBenefitFor(account, dining);
		AccountContribution contribution = account.makeContribution(amount);
		return rewardRepository.confirmReward(contribution, dining);
	}
}