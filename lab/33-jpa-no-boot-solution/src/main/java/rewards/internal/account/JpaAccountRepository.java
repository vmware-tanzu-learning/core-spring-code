package rewards.internal.account;

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
	public Account findByCreditCardNumber(String creditCardNumber) {
		String jpql = "SELECT a FROM Account a where a.creditCardNumber = :creditCardNumber";
		Account account = entityManager.createQuery(jpql, Account.class)
				.setParameter("creditCardNumber", creditCardNumber).getSingleResult();
		return account;
	}

}
