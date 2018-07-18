package rewards.internal.account;

/**
 * Loads account aggregates. Called by the reward network to find and reconstitute Account entities from an external
 * form such as a set of RDMS rows.
 * 
 * Objects returned by this repository are guaranteed to be fully-initialized and ready to use.
 */
public interface AccountRepository {

	/**
	 * Indicates implementation being used. Actual implementation is hidden
	 * behind a proxy, so this makes it easy to determine when testing.
	 * 
	 * @return Implementation information.
	 */
	public String getInfo();

	/**
	 * Load an account by its credit card.
	 * @param creditCardNumber the credit card number
	 * @return the account object
	 */
	public Account findByCreditCard(String creditCardNumber);

}