package accounts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

import accounts.AccountManager;

/**
 * A Spring MVC @Controller controller handling requests to view and modify
 * Account information.
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
	 * Provide a model with a list of all accounts for the account List page.
	 * </p>
	 * 
	 * @param model
	 *            the "implicit" model created by Spring MVC
	 */
	@RequestMapping("/accountList")
	public String accountList(Model model) {
		model.addAttribute("accounts", accountManager.getAllAccounts());

		// TODO-03: Refactor this to return just the logical view name
		return "/WEB-INF/views/accountList.jsp";
	}

	// TODO-06: Implement the /accountDetails request handling method.
	// 1. Use a method parameter to obtain the request parameter needed to
	// retrieve an account.
	// 2. Use another method parameter to gain access to the Model.
	// 3. Use the accountManager to obtain an account. Place this on the model.
	// - the attribute key should correspond to the key used in the JSP file
	// (see src/main/webapp/WEB-INF/views/accountDetails.jsp).
	// 4. Finally tell Spring to forward to accountDetails.jsp by returning the
	// correct logical view name.
	// 5. Save all work

}
