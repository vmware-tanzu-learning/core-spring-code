package accounts.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.orm.ObjectRetrievalFailureException;

import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

import common.money.Percentage;

/**
 * IMPORTANT: Per best practices, this class shouldn't be in 'src/main/java'
 * but rather in 'src/test/java'. However, it is used by numerous Test classes
 * inside multiple projects. Maven does not provide an easy way to access a
 * class that is inside another project's 'src/test/java' folder.
 *<p>
 * Rather than using some complex Maven configuration, we decided it is
 * acceptable to place this test class inside 'src/main/java'.
 */
public class StubAccountManager extends AbstractAccountManager {

	public static final int NUM_ACCOUNTS_IN_STUB = 1;

	public static final long TEST_ACCOUNT_ID = 0L;
	public static final String TEST_ACCOUNT_NUMBER = "123456789";
	public static final String TEST_ACCOUNT_NAME = "Keith and Keri Donald";

	public static final long TEST_BEN0_ID = 0L;
	public static final String TEST_BEN0_NAME = "Annabelle";
	public static final long TEST_BEN1_ID = 1L;
	public static final String TEST_BEN1_NAME = "Corgan";
	public static final String BENEFICIARY_SHARE = "50%";

	private Map<Long, Account> accountsById = new HashMap<Long, Account>();

	private AtomicLong nextEntityId = new AtomicLong(3);

	public StubAccountManager() {
		// One test account
		Account account = new Account(TEST_ACCOUNT_NUMBER, TEST_ACCOUNT_NAME);
		account.setEntityId(TEST_ACCOUNT_ID);

		// Two test beneficiaries
		account.addBeneficiary(TEST_BEN0_NAME, Percentage.valueOf(BENEFICIARY_SHARE));
		account.addBeneficiary(TEST_BEN1_NAME, Percentage.valueOf(BENEFICIARY_SHARE));

		// Retrieve each Beneficiary and set its entityId
		account.getBeneficiary(TEST_BEN0_NAME).setEntityId(TEST_BEN0_ID);
		account.getBeneficiary(TEST_BEN1_NAME).setEntityId(TEST_BEN1_ID);

		// Save the account
		accountsById.put(0L, account);
	}

	@Override
	public List<Account> getAllAccounts() {
		return new ArrayList<Account>(accountsById.values());
	}

	@Override
	public Account getAccount(Long id) {
		return accountsById.get(id);
	}

	@Override
	public Account save(Account newAccount) {
		for (Beneficiary beneficiary : newAccount.getBeneficiaries()) {
			beneficiary.setEntityId(nextEntityId.getAndIncrement());
		}

		newAccount.setEntityId(nextEntityId.getAndIncrement());
		accountsById.put(newAccount.getEntityId(), newAccount);
		return newAccount;
	}

	@Override
	public void update(Account account) {
		accountsById.put(account.getEntityId(), account);
	}

	@Override
	public void updateBeneficiaryAllocationPercentages(Long accountId, Map<String, Percentage> allocationPercentages) {
		Account account = accountsById.get(accountId);
		for (Entry<String, Percentage> entry : allocationPercentages.entrySet()) {
			account.getBeneficiary(entry.getKey()).setAllocationPercentage(entry.getValue());
		}
	}

	@Override
	public void addBeneficiary(Long accountId, String beneficiaryName) {
		accountsById.get(accountId).addBeneficiary(beneficiaryName, Percentage.zero());
	}

	@Override
	public void removeBeneficiary(Long accountId, String beneficiaryName,
			Map<String, Percentage> allocationPercentages) {
		accountsById.get(accountId).removeBeneficiary(beneficiaryName);
		updateBeneficiaryAllocationPercentages(accountId, allocationPercentages);
	}

}
