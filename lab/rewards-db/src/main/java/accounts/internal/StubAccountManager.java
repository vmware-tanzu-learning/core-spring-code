package accounts.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;
import accounts.AccountManager;

import common.money.Percentage;

/**
 * IMPORTANT!!!
 * Per best practices, this class shouldn't be in 'src/main/java' but rather in 'src/test/java'.
 * However, it is used by numerous Test classes inside multiple projects. Maven does not 
 * provide an easy way to access a class that is inside another project's 'src/test/java' folder.
 * 
 * Rather than using some complex Maven configuration, we decided it is acceptable to place this test 
 * class inside 'src/main/java'.
 *
 */
public class StubAccountManager implements AccountManager {

	public static final String INFO = "Stub";

	public static final int NUM_ACCOUNTS_IN_STUB = 1;

	private Map<Long, Account> accountsById = new HashMap<Long, Account>();

	private AtomicLong nextEntityId = new AtomicLong(3);

	public StubAccountManager() {
		Account account = new Account("123456789", "Keith and Keri Donald");
		account.addBeneficiary("Annabelle", Percentage.valueOf("50%"));
		account.addBeneficiary("Corgan", Percentage.valueOf("50%"));
		account.setEntityId(0L);
		account.getBeneficiary("Annabelle").setEntityId(0L);
		account.getBeneficiary("Corgan").setEntityId(1L);
		accountsById.put(0L, account);
		
		Logger.getLogger(StubAccountManager.class).info("Created StubAccountManager");
	}

	@Override
	public String getInfo() {
		return INFO;
	}

	@Override
	public List<Account> getAllAccounts() {
		return new ArrayList<Account>(accountsById.values());
	}

	@Override
	public Account getAccount(Long id) {
		Account account = accountsById.get(id);
		if (account == null) {
			throw new ObjectRetrievalFailureException(Account.class, id);
		}
		return account;
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
	public void removeBeneficiary(Long accountId, String beneficiaryName, Map<String, Percentage> allocationPercentages) {
		accountsById.get(accountId).removeBeneficiary(beneficiaryName);
		updateBeneficiaryAllocationPercentages(accountId, allocationPercentages);
	}

}
