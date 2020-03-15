package rewards.internal.account;

/**
 * Loads account aggregates. Called by the reward network to find and
 * reconstitute Account entities from an external form such as a set of RDMS
 * rows.
 *
 * Objects returned by this repository are guaranteed to be fully initialized
 * and ready to use.
 */
//  TODO-03: Alter this interface to extend a proper Spring Data interface.
//  - The finder method on this class must be changed to obey Spring Data
//    conventions - use refactoring feature of the IDE
public interface AccountRepository {

	/**
	 * Load an account by its credit card.
	 *
	 * @param creditCardNumber
	 *            the credit card number
	 * @return the account object
	 */
	// To refactor: right click on the method name -> Refactor -> Rename
	public Account findByCreditCard(String creditCardNumber);

}