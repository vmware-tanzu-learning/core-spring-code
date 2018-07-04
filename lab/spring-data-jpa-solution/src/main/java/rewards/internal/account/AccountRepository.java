package rewards.internal.account;

import org.springframework.data.repository.Repository;

/**
 * Loads account aggregates. Called by the reward network to find and
 * reconstitute Account entities from an external form such as a set of RDMS
 * rows.
 * 
 * Objects returned by this repository are guaranteed to be fully initialized
 * and ready to use.
 */
public interface AccountRepository extends Repository<Account,Long> {

	/**
	 * Load an account by its credit card.
	 * 
	 * @param creditCardNumber
	 *            the credit card number
	 * @return the account object
	 */
	public Account findByCreditCardNumber(String creditCardNumber);

}