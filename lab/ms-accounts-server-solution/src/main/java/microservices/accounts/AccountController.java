package microservices.accounts;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import accounts.AccountManager;
import rewards.internal.account.Account;

/**
 * A RESTFul controller for accessing account information from an RDB.
 */
@RestController
public class AccountController {

	protected Logger logger = Logger
			.getLogger(AccountController.class.getName());
	protected AccountManager accountManager;

	/**
	 * Create an instance plugging in the respository of Accounts.
	 * 
	 * @param accountRepository
	 *            An account repository implementation.
	 */
	@Autowired
	public AccountController(AccountManager accountManager) {
		this.accountManager = accountManager;

		logger.info("AccountRepository says system has "
				+ accountManager.getAllAccounts().size() + " accounts");
	}

	/**
	 * Fetch an account with the specified account number.
	 * 
	 * @param accountNumber
	 *            A numeric, 9 digit account number.
	 * @return The account if found.
	 * @throws AccountNotFoundException
	 *             If the number is not recognized.
	 */
	@GetMapping("/accounts")
	public Account[] all() {

		logger.info("accounts-microservice all() invoked");
		List<Account> accounts = accountManager.getAllAccounts();
		logger.info("accounts-microservice all() found: " + accounts.size());
		return accounts.toArray(new Account[accounts.size()]);
	}

	/**
	 * Fetch an account with the specified entityId.
	 * 
	 * @param id
	 *            A numeric entityId.
	 * @return The account if found.
	 * @throws AccountNotFoundException
	 *             If the id is not recognized.
	 */
	@GetMapping("/accounts/{id}")
	public Account byId(@PathVariable("id") Long id) {

		try {
			logger.info("accounts-microservice byId() invoked: " + id);
			Account account = accountManager.getAccount(id);
			logger.info("accounts-microservice byId() found: " + account);

			if (account == null) {
				logger.info("accounts-microservice byId() failed: AccountNotFoundException");
				throw new AccountNotFoundException(id);
			} else {
				logger.info("accounts-microservice byId() success");
				return account;
			}
		} catch (ObjectRetrievalFailureException e) {
			logger.info("accounts-microservice byId() failed: AccountNotFoundException");
			throw new AccountNotFoundException(id);
		}
	}

}
