package accounts.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import accounts.AccountManager;
import common.money.MonetaryAmount;
import common.money.Percentage;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

/**
 * Integration test for an account manager implementation.
 * <p>
 * Tests application behavior to verify the AccountManager and the database
 * mapping of its domain objects are correct. The implementation of the
 * AccountManager class is irrelevant to these tests and so is the testing
 * environment (stubbing, manual or Spring-driven configuration).
 */
public abstract class AbstractAccountManagerTests {

	protected final Logger logger;

	@Autowired
	protected AccountManager accountManager;

	public AbstractAccountManagerTests() {
		logger = Logger.getLogger(getClass());
		logger.setLevel(Level.INFO);
	}

	/**
	 * Quick test to check the right profile is being used.
	 */
	@Test
	public abstract void testProfile();

	/**
	 * How many accounts are defined for use in the test?
	 * 
	 * @return Number of accounts available.
	 */
	protected abstract int getNumAccountsExpected();

	/**
	 * Used to log current transactional status.
	 */
	protected abstract void showStatus();

	@Test
	@Transactional
	public void testGetAllAccounts() {
		showStatus();
		List<Account> accounts = accountManager.getAllAccounts();
		assertEquals("Wrong number of accounts", getNumAccountsExpected(), accounts.size());
	}

	@Test
	@Transactional
	public void getAccount() {
		Account account = accountManager.getAccount(0L);
		// assert the returned account contains what you expect given the state
		// of the database
		assertNotNull("account should never be null", account);
		assertEquals("wrong entity id", 0L, account.getEntityId().longValue());
		assertEquals("wrong account number", "123456789", account.getNumber());
		assertEquals("wrong name", "Keith and Keri Donald", account.getName());
		assertEquals("wrong beneficiary collection size", 2, account.getBeneficiaries().size());

		Beneficiary b1 = account.getBeneficiary("Annabelle");
		assertNotNull("Annabelle should be a beneficiary", b1);
		assertEquals("wrong savings", MonetaryAmount.valueOf("0.00"), b1.getSavings());
		assertEquals("wrong allocation percentage", Percentage.valueOf("50%"), b1.getAllocationPercentage());

		Beneficiary b2 = account.getBeneficiary("Corgan");
		assertNotNull("Corgan should be a beneficiary", b2);
		assertEquals("wrong savings", MonetaryAmount.valueOf("0.00"), b2.getSavings());
		assertEquals("wrong allocation percentage", Percentage.valueOf("50%"), b2.getAllocationPercentage());
	}

	@Test
	@Transactional
	public void addAccount() {
		// FIX ME: - Manual configuration fails, Spring driven integration tests work
		// OK.
		if (accountManager instanceof JpaAccountManager
				&& this.getClass().getAnnotation(ContextConfiguration.class) == null)
			return;

		//long newAccountId = getNumAccountsExpected();

		Account account = new Account("1010101", "Test");
		account.addBeneficiary("Bene1", Percentage.valueOf("100%"));

		showStatus();
		Account newAccount = accountManager.save(account);

		assertEquals("Wrong number of accounts", getNumAccountsExpected() + 1, accountManager.getAllAccounts().size());

		newAccount = accountManager.getAccount(newAccount.getEntityId());
		assertNotNull("Did not find new account", newAccount);
		assertEquals("Did not save account", "Test", newAccount.getName());
		assertEquals("Did not save beneficiary", 1, newAccount.getBeneficiaries().size());
	}

	@Test
	@Transactional
	public void updateAccount() {
		Account oldAccount = accountManager.getAccount(0L);
		oldAccount.setName("Ben Hale");
		accountManager.update(oldAccount);
		Account newAccount = accountManager.getAccount(0L);
		assertEquals("Did not persist the name change", "Ben Hale", newAccount.getName());
	}

	@Test
	@Transactional
	public void updateAccountBeneficiaries() {
		Map<String, Percentage> allocationPercentages = new HashMap<String, Percentage>();
		allocationPercentages.put("Annabelle", Percentage.valueOf("25%"));
		allocationPercentages.put("Corgan", Percentage.valueOf("75%"));
		accountManager.updateBeneficiaryAllocationPercentages(0L, allocationPercentages);
		Account account = accountManager.getAccount(0L);
		assertEquals("Invalid adjusted percentage", Percentage.valueOf("25%"),
				account.getBeneficiary("Annabelle").getAllocationPercentage());
		assertEquals("Invalid adjusted percentage", Percentage.valueOf("75%"),
				account.getBeneficiary("Corgan").getAllocationPercentage());
	}

	@Test
	@Transactional
	public void addBeneficiary() {
		accountManager.addBeneficiary(0L, "Ben");
		Account account = accountManager.getAccount(0L);
		assertEquals("Should only have three beneficiaries", 3, account.getBeneficiaries().size());
	}

	@Test
	@Transactional
	public void removeBeneficiary() {
		Map<String, Percentage> allocationPercentages = new HashMap<String, Percentage>();
		allocationPercentages.put("Corgan", Percentage.oneHundred());
		accountManager.removeBeneficiary(0L, "Annabelle", allocationPercentages);
		Account account = accountManager.getAccount(0L);
		assertEquals("Should only have one beneficiary", 1, account.getBeneficiaries().size());
		assertEquals("Corgan should now have 100% allocation", Percentage.oneHundred(),
				account.getBeneficiary("Corgan").getAllocationPercentage());
	}

}
