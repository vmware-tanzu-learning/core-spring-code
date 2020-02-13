package accounts.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import common.money.Percentage;
import rewards.internal.account.Account;

/**
 * An account manager that uses JPA to find accounts.
 */
@Repository
public class JpaAccountManager extends AbstractAccountManager {

	private EntityManager entityManager;

	/**
	 * Creates a new JPA account manager.
	 * <p>
	 * Its entityManager will be set automatically by
	 * {@link #setEntityManager(EntityManager)}.
	 */
	public JpaAccountManager() {
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Account> getAllAccounts() {
		List<Account> l = entityManager.createQuery("select a from Account a LEFT JOIN FETCH a.beneficiaries")
				.getResultList();

		// Use of "JOIN FETCH" produces duplicate accounts, and DISTINCT does
		// not address this. So we have to filter it manually.
		List<Account> result = new ArrayList<Account>();

		for (Account a : l) {
			if (!result.contains(a))
				result.add(a);
		}

		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Account getAccount(Long id) {
		Account account = (Account) entityManager.find(Account.class, id);

		if (account != null) {
			// Force beneficiaries to load too - avoid Hibernate lazy loading error
			account.getBeneficiaries().size();
		}

		return account;
	}

	@Override
	@Transactional
	public Account save(Account account) {
		entityManager.persist(account);
		return account;
	}

	@Override
	@Transactional
	public void update(Account account) {
		entityManager.merge(account);
	}

	@Override
	@Transactional
	public void updateBeneficiaryAllocationPercentages(Long accountId, Map<String, Percentage> allocationPercentages) {
		Account account = getAccount(accountId);
		for (Entry<String, Percentage> entry : allocationPercentages.entrySet()) {
			account.getBeneficiary(entry.getKey()).setAllocationPercentage(entry.getValue());
		}
	}

	@Override
	@Transactional
	public void addBeneficiary(Long accountId, String beneficiaryName) {
		getAccount(accountId).addBeneficiary(beneficiaryName, Percentage.zero());
	}

	@Override
	@Transactional
	public void removeBeneficiary(Long accountId, String beneficiaryName,
			Map<String, Percentage> allocationPercentages) {
		getAccount(accountId).removeBeneficiary(beneficiaryName);

		if (allocationPercentages != null)
			updateBeneficiaryAllocationPercentages(accountId, allocationPercentages);
	}

}
