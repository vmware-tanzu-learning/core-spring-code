package accounts.web;

import accounts.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import rewards.internal.account.Account;

import java.util.List;

/**
 * A Spring MVC REST Controller handling requests to view Account information.
 *
 * Note that some of the Account related classes are imported from the
 * rewards-db project:
 *
 * -Domain objects: Account and  Beneficiary
 * -Service layer: AccountManager interface
 * -Repository layer: AccountRepository interface
 *
 */
@RestController
public class AccountController {

	private final AccountManager accountManager;

	/**
	 * Creates a new AccountController with a given account manager.
	 */
	@Autowired
	public AccountController(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	/**
	 * Provide a model with an account for the account detail page.
	 */
	@GetMapping("/accounts/{entityId}")
	public Account accountDetails(@PathVariable("entityId") long id) {
		return accountManager.getAccount(id);
	}

	/**
	 * Provide a model with a list of all accounts for the account List page.
	 */
	@GetMapping("/accounts")
	public List<Account> accountList() {
		return accountManager.getAllAccounts();
	}
}
