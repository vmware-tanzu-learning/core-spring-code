package accounts.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import accounts.AccountManager;
import common.util.ThreadUtils;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

/**
 * A Spring MVC @Controller controller handling requests to fetch or view
 * Account information. This is a simplified version of the controller from
 * <code>rest-ws</code> since we only need to fetch accounts by id.
 * <p>
 * Note that all the Account application classes are imported from the
 * <tt>rewards-db</tt> project:
 * <ul>
 * <li>Domain objects: {@link Account} and {@link Beneficiary}</li>
 * <li>Service layer: {@link AccountManager} interface and its
 * implementations</li>
 * <li>No repository layer is being used - the account-manager does
 * everything</li>
 */
@Controller
public class AccountController {

	private AccountManager accountManager;

	/**
	 * Creates a new AccountController with a given account manager.
	 */
	@Autowired
	public AccountController(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	/**
	 * <p>
	 * Provide a model with an account for the account detail page.
	 * </p>
	 * 
	 * @param id
	 *            the id of the account
	 * @param model
	 *            the "implicit" model created by Spring MVC
	 */
	@RequestMapping("/accountDetails")
	public String accountDetails(@RequestParam("entityId") long id, Model model) {
		model.addAttribute("account", accountManager.getAccount(id));
		return "accountDetails";
	}

	/**
	 * <p>
	 * Provide a model with a list of all accounts for the account List page.
	 * </p>
	 * 
	 * @param model
	 *            the "implicit" model created by Spring MVC
	 */
	@RequestMapping("/accountList")
	public String accountList(Model model) {
		model.addAttribute("accounts", accountManager.getAllAccounts());
		return "accountList";
	}

	/**
	 * <p>
	 * RESTful method to fetch an account.
	 * </p>
	 * 
	 * @param id
	 *            The account id from the URL
	 */
	@RequestMapping("/accounts")
	public @ResponseBody List<Account> allAccounts() {
		return accountManager.getAllAccounts();
	}

	/**
	 * <p>
	 * RESTful method to fetch an account.
	 * </p>
	 * 
	 * @param id
	 *            The account id from the URL
	 */
	@RequestMapping("/accounts/{id}")
	public @ResponseBody Account accounts(@PathVariable long id) {
		// LoggerFactory.getLogger(getClass()).info("Get account: " + id);

		// Slow this controller down so we can see the advantage of WebClient running
		// multiple requests concurrently
		ThreadUtils.delay(50);

		return accountManager.getAccount(id);
	}

}
