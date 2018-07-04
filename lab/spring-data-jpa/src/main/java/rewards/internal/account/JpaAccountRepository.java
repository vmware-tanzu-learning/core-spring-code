package rewards.internal.account;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * An account repository that uses JPA to find accounts.
 */
@Repository("accountRepository")
public class JpaAccountRepository implements AccountRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Account findByCreditCard(String creditCardNumber) {
		String jpql = "SELECT a FROM Account a where a.creditCardNumber = :creditCardNumber";
		List<Account> accounts = entityManager.createQuery(jpql, Account.class)
				                              .setParameter("creditCardNumber", creditCardNumber)
				                              .getResultList();
		return accounts.get(0);
	}

}
