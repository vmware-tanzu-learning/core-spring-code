package accounts;

import java.util.List;
import java.util.Map;

import rewards.internal.account.Account;

import common.money.Percentage;

/**
 * Manages access to account information. Used as the service layer component in
 * the <tt>mvc</tt> and <tt>security</tt> projects.
 */
public interface AccountManager {

	/**
	 * Indicates implementation being used. Actual implementation is hidden
	 * behind a proxy, so this makes it easy to determine.
	 * 
	 * @return Implementation information.
	 */
	public String getInfo();

	/**
	 * Get all accounts in the system
	 * 
	 * @return all accounts
	 */
	public List<Account> getAllAccounts();

	/**
	 * Find an account by its number.
	 * 
	 * @param id
	 *            the account id
	 * @return the account
	 */
	public Account getAccount(Long id);

	/**
	 * Takes a transient account and persists it.
	 * 
	 * @param account
	 *            The account to save
	 * @return The persistent account - this may or may not be the same object
	 *         as the method argument.
	 */
	public Account save(Account account);

	/**
	 * Takes a changed account and persists any changes made to it.
	 * 
	 * @param account
	 *            The account with changes
	 */
	public void update(Account account);

	/**
	 * Updates the allocation percentages for the entire collection of
	 * beneficiaries in an account
	 * 
	 * @param accountId
	 *            the account id
	 * @param allocationPercentages
	 *            A map of beneficiary names and allocation percentages, keyed
	 *            by beneficiary name
	 */
	public void updateBeneficiaryAllocationPercentages(Long accountId,
			Map<String, Percentage> allocationPercentages);

	/**
	 * Adds a beneficiary to an account. The new beneficiary will have a 0
	 * allocation percentage.
	 * 
	 * @param accountId
	 *            the account id
	 * @param beneficiaryName
	 *            the name of the beneficiary to remove
	 */
	public void addBeneficiary(Long accountId, String beneficiaryName);

	/**
	 * Removes a beneficiary from an account.
	 * 
	 * @param accountId
	 *            the account id
	 * @param beneficiaryName
	 *            the name of the beneficiary to remove
	 * @param allocationPercentages
	 *            new allocation percentages, keyed by beneficiary name
	 */
	public void removeBeneficiary(Long accountId, String beneficiaryName,
			Map<String, Percentage> allocationPercentages);
}
