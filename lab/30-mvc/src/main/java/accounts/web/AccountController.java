package accounts.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import accounts.AccountManager;
import rewards.internal.account.Account;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.Beneficiary;

/**
 * A Spring MVC REST Controller handling requests to view Account information.
 * <p>
 * Note that some of the Account related classes are imported from the
 * <tt>rewards-db</tt> project:
 * <ul>
 * <li>Domain objects: {@link Account} and {@link Beneficiary}</li>
 * <li>Service layer: {@link AccountManager} interface</li>
 * <li>Repository layer: {@link AccountRepository} interface</li>
 * </ul>
 */
// TODO-03: Add the annotation for Spring MVC to recognize this class
// as a REST controller.
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
	// TODO-04: Add the mapping for /accounts
	public List<Account> accountList() {

		// TODO-05: Implement the logic to find and return all accounts
		// - Save the changes, wait for the application to restart
		// - From the home page, click the link - this should now work
		// - If you prefer, access http://localhost:8080/accounts using curl or Postman

		return null; // REPLACE THIS LINE to return a list accounts
		
		// TODO-06: We are about to make lots of changes, so stop the application
		//          otherwise Devtools will keep restarting it. 
	}

	// TODO-09: Implement the /accountDetails request handling method.
	// 1. Call the method accountDetails().
	// 2. Annotate to define URL mapping /accounts/{entityId} this method will
	// respond to.
	// 3. Use a method parameter to obtain the URI template parameter needed to
	// retrieve an account.
	// 4. Use the accountManager to obtain an account. This is the value to return
	// 5. Save all work

	// TODO-10: Run the test in AccountControllerTests, it should pass.
	//          Fix any errors before moving on

	// TODO-11: Run the application as a Spring Boot or Java application in your IDE.
	// You should now be able to invoke http://localhost:8080/accounts/N
	// where N is 0-20 and get a response. You can use curl, Postman or your browser
	// to do this.

}
