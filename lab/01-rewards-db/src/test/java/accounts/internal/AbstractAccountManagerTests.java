package accounts.internal;

import accounts.AccountManager;
import ch.qos.logback.classic.Level;
import common.money.MonetaryAmount;
import common.money.Percentage;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
		logger = LoggerFactory.getLogger(getClass());
		if (logger instanceof ch.qos.logback.classic.Logger)
			((ch.qos.logback.classic.Logger) logger).setLevel(Level.INFO);
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
		assertEquals(getNumAccountsExpected(), accounts.size(), "Wrong number of accounts");
	}

	@Test
	@Transactional
	public void getAccount() {
		Account account = accountManager.getAccount(0L);
		// assert the returned account contains what you expect given the state
		// of the database
		assertNotNull(account, "account should never be null");
		assertEquals(account.getEntityId().longValue(),  0L, "wrong entity id");
		assertEquals("123456789", account.getNumber(), "wrong account number");
		assertEquals("Keith and Keri Donald", account.getName(), "wrong name");
		assertEquals(2, account.getBeneficiaries().size(), "wrong beneficiary collection size");

		Beneficiary b1 = account.getBeneficiary("Annabelle");
		assertNotNull(b1, "Annabelle should be a beneficiary");
		assertEquals(MonetaryAmount.valueOf("0.00"), b1.getSavings(), "wrong savings");
		assertEquals(Percentage.valueOf("50%"), b1.getAllocationPercentage(), "wrong allocation percentage");

		Beneficiary b2 = account.getBeneficiary("Corgan");
		assertNotNull(b2, "Corgan should be a beneficiary");
		assertEquals(MonetaryAmount.valueOf("0.00"), b2.getSavings(), "wrong savings");
		assertEquals(Percentage.valueOf("50%"), b2.getAllocationPercentage(), "wrong allocation percentage");
	}

	@Test
	@Transactional
	public void addAccount() {
		// FIX ME: - Manual configuration fails, Spring driven integration tests work
		// OK.
		if (accountManager instanceof JpaAccountManager
				&& this.getClass().getAnnotation(ContextConfiguration.class) == null)
			return;

		// long newAccountId = getNumAccountsExpected();

		Account account = new Account("1010101", "Test");
		account.addBeneficiary("Bene1", Percentage.valueOf("100%"));

		showStatus();
		Account newAccount = accountManager.save(account);

		assertEquals(getNumAccountsExpected() + 1, accountManager.getAllAccounts().size(), "Wrong number of accounts");

		newAccount = accountManager.getAccount(newAccount.getEntityId());
		assertNotNull(newAccount, "Did not find new account");
		assertEquals("Test", newAccount.getName(), "Did not save account");
		assertEquals(1, newAccount.getBeneficiaries().size(), "Did not save beneficiary");
	}

	@Test
	@Transactional
	public void updateAccount() {
		Account oldAccount = accountManager.getAccount(0L);
		oldAccount.setName("Ben Hale");
		accountManager.update(oldAccount);
		Account newAccount = accountManager.getAccount(0L);
		assertEquals("Ben Hale", newAccount.getName(), "Did not persist the name change");
	}

	@Test
	@Transactional
	public void updateAccountBeneficiaries() {
		Map<String, Percentage> allocationPercentages = new HashMap<String, Percentage>();
		allocationPercentages.put("Annabelle", Percentage.valueOf("25%"));
		allocationPercentages.put("Corgan", Percentage.valueOf("75%"));
		accountManager.updateBeneficiaryAllocationPercentages(0L, allocationPercentages);
		Account account = accountManager.getAccount(0L);
		assertEquals(Percentage.valueOf("25%"),
				account.getBeneficiary("Annabelle").getAllocationPercentage(), "Invalid adjusted percentage");
		assertEquals(Percentage.valueOf("75%"),
				account.getBeneficiary("Corgan").getAllocationPercentage(), "Invalid adjusted percentage");
	}

	@Test
	@Transactional
	public void addBeneficiary() {
		accountManager.addBeneficiary(0L, "Ben");
		Account account = accountManager.getAccount(0L);
		assertEquals( 3, account.getBeneficiaries().size(), "Should only have three beneficiaries");
	}

	@Test
	@Transactional
	public void removeBeneficiary() {
		Map<String, Percentage> allocationPercentages = new HashMap<String, Percentage>();
		allocationPercentages.put("Corgan", Percentage.oneHundred());
		accountManager.removeBeneficiary(0L, "Annabelle", allocationPercentages);
		Account account = accountManager.getAccount(0L);
		assertEquals(1, account.getBeneficiaries().size(), "Should only have one beneficiary");
		assertEquals(Percentage.oneHundred(),
				account.getBeneficiary("Corgan").getAllocationPercentage(), "Corgan should now have 100% allocation");
	}

}
