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
// TODO-05 Add the annotation for Spring MVC to recognize this class
// - Save the change, wait for the application to restart
// - From the home page, navigate to List Accounts - this should now work
// - Clicking on any individual account fails - you will implement that shortly
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

		// TODO-07a: Notice this is returning the full path to the template
		// * Long template paths quickly get very tedious
		// * Change it to return the logical view-name "accountList"
		return "classpath:/templates/accountList.html";
	}

	// TODO-09: Implement the /accountDetails request handling method.
	// 1. Use a method parameter to obtain the request parameter needed to
	// retrieve an account.
	// 2. Use another method parameter to gain access to the Model.
	// 3. Use the accountManager to obtain an account. Place this on the model.
	// - the attribute name should correspond to the {{variable}} used in the
	// - template file(see src/main/resources/templates/accountDetails.html).
	// 4. Finally tell Spring to use this template by returning the correct
	// - logical view name.
	// 5. Save all work


	// TODO-11: You should now be able to click any of the account links and reach
	// their details page.

}
