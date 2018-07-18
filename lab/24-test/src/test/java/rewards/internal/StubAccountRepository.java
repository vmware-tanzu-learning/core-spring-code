package rewards.internal;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import rewards.internal.account.Account;
import rewards.internal.account.AccountRepository;

import common.money.Percentage;

/**
 * A dummy account repository implementation. Has a single Account
 * "Keith and Keri Donald" with two beneficiaries "Annabelle" (50% allocation)
 * and "Corgan" (50% allocation) associated with credit card "1234123412341234".
 * 
 * Stubs facilitate unit testing. An object needing an AccountRepository can
 * work with this stub and not have to bring in expensive and/or complex
 * dependencies such as a Database. Simple unit tests can then verify object
 * behavior by considering the state of this stub.
 */
public class StubAccountRepository implements AccountRepository {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Account> accountsByCreditCard = new HashMap<String, Account>();

	/**
	 * Creates a single test account with two beneficiaries. Also logs creation
	 * so we know which repository we are using.
	 */
	public StubAccountRepository() {
		logger.info("Creating " + getClass().getSimpleName());
		Account account = new Account("123456789", "Keith and Keri Donald");
		account.addBeneficiary("Annabelle", Percentage.valueOf("50%"));
		account.addBeneficiary("Corgan", Percentage.valueOf("50%"));
		accountsByCreditCard.put("1234123412341234", account);
	}

	public Account findByCreditCard(String creditCardNumber) {
		Account account = accountsByCreditCard.get(creditCardNumber);
		if (account == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return account;
	}

	public void updateBeneficiaries(Account account) {
		// nothing to do, everything is in memory
	}
}