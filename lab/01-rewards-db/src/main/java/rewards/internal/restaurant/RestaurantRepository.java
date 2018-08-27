package rewards.internal.restaurant;

/**
 * Loads restaurant aggregates. Called by the reward network to find and
 * reconstitute Restaurant entities from an external form such as a set of RDMS
 * rows.
 * 
 * Objects returned by this repository are guaranteed to be fully-initialized
 * and ready to use.
 */
public interface RestaurantRepository {

	/**
	 * Indicates implementation being used. Actual implementation is hidden
	 * behind a proxy, so this makes it easy to determine when testing.
	 * 
	 * @return Implementation information.
	 */
	public String getInfo();

	/**
	 * Load a Restaurant entity by its merchant number.
	 * 
	 * @param merchantNumber
	 *            the merchant number
	 * @return the restaurant
	 */
	public Restaurant findByMerchantNumber(String merchantNumber);

	/**
	 * Find the number of restaurants in the repository.
	 * 
	 * @return The number of restaurants - zero or more.
	 */
	public Long getRestaurantCount();
}
