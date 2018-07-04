package rewards.internal.account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

/**
 * An account repository that uses JPA to find accounts.
 */
public class JpaAccountRepository implements AccountRepository {

	public static final String INFO = "JPA";

	private static final Logger logger = Logger.getLogger("config");
	
	private EntityManager entityManager;

	public JpaAccountRepository() {
		logger.info("Created JpaAccountManager");
	}

	/**
	 * Creates a new JPA account manager.
	 * 
	 * @param entityManager
	 *            the JPA entity manager
	 */
	public JpaAccountRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
		logger.info("Created JpaAccountManager");
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public String getInfo() {
		return INFO;
	}

	public Account findByCreditCard(String creditCardNumber) {
		// Find id account of account with this credit-card using a direct
		// SQL query on the unmapped T_ACCOUNT_CREDIT_CARD table.
		Integer accountId = (Integer) entityManager
				.createNativeQuery("select ACCOUNT_ID from T_ACCOUNT_CREDIT_CARD where NUMBER = :ccn")
				.setParameter("ccn", creditCardNumber).getSingleResult();

		Account account = (Account) entityManager.find(Account.class, accountId.longValue());

		// Force beneficiaries to load too - avoid Hibernate lazy loading error
		account.getBeneficiaries().size();

		return account;
	}

}