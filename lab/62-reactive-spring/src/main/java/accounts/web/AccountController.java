package accounts.web;

import accounts.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import rewards.internal.account.Account;

import java.util.List;

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
	 * Provide a model with an account for the account detail page.
	 */
	@RequestMapping("/accountDetails")
	public String accountDetails(@RequestParam("entityId") long id, Model model) {
		model.addAttribute("account", accountManager.getAccount(id));
		return "accountDetails";
	}

	/**
	 * Provide a model with a list of all accounts for the account List page.
	 */
	@RequestMapping("/accountList")
	public String accountList(Model model) {
		model.addAttribute("accounts", accountManager.getAllAccounts());
		return "accountList";
	}

	/**
	 * RESTful method to fetch an account.
	 */
	@RequestMapping("/accounts")
	public @ResponseBody List<Account> allAccounts() {
		return accountManager.getAllAccounts();
	}

	/**
	 * RESTful method to fetch an account.
	 */
	@RequestMapping("/accounts/{id}")
	public @ResponseBody Account accounts(@PathVariable long id) {
		// LoggerFactory.getLogger(getClass()).info("Get account: " + id);

		// TODO-10: Slow this controller down so we can see the advantage of WebClient
		// running multiple requests concurrently. Uncomment the next line to add a 50ms
		// delay

		// ThreadUtils.delay(50);

		return accountManager.getAccount(id);
	}

}
