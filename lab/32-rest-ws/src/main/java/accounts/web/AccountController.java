package accounts.web;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import accounts.AccountManager;
import common.money.Percentage;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

/**
 * A controller handling requests for CRUD operations on Accounts and their
 * Beneficiaries.
 */
@Controller
public class AccountController {

	private final Logger logger = Logger.getLogger(getClass());

	private AccountManager accountManager;

	/**
	 * Creates a new AccountController with a given account manager.
	 */
	@Autowired
	public AccountController(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	/**
	 * Provide a list of all accounts.
	 */
	// TODO 02: Complete this method. Add annotations to:
	//   a. Respond to GET /accounts
    //   b. Return a List<Account> to be converted to the response body
	// Save your work and restart the application.
	// You should get JSON results in your browser when accessing http://localhost:8080/accounts
	public List<Account> accountSummary() {
		return accountManager.getAllAccounts();
	}

	/**
	 * Provide the details of an account with the given id.
	 */
	// TODO 04: Complete this method. Add annotations to:
	//   a. Respond to GET /accounts/{accountId}
    //   b. Return  an Account to be converted to the response body
	// Save your work and restart the application.
	// You should get JSON results in your browser when accessing http://localhost:8080/accounts/0
	public Account accountDetails(int id) {
		return retrieveAccount(id);
	}

	/**
	 * Creates a new Account, setting its URL as the Location header on the
	 * response.
	 */
	// TODO 06: Complete this method. Add annotations to:
	//  a. Respond to POST /accounts requests
    //  b. Automatically get an unmarshaled Account from the request
    //  c. Indicate a "201 Created" status
	public ResponseEntity<Void> createAccount(Account newAccount) {
		// Saving the account also sets its entity Id
		Account account = accountManager.save(newAccount);

		// Return a ResponseEntity - it will be used to build the
		// HttpServletResponse.
		return entityWithLocation(account.getEntityId());
	}

	/**
	 * Return a response with the location of the new resource. It's URL is
	 * assumed to be a child of the URL just received.
	 * <p>
	 * Suppose we have just received an incoming URL of, say,
	 * <code>http://localhost:8080/accounts</code> and <code>resourceId</code>
	 * is "1111". Then the URL of the new resource will be
	 * <code>http://localhost:8080/accounts/1111</code>.
	 * 
	 * @param resourceId
	 *            Is of the new resource.
	 * @return
	 */
	private ResponseEntity<Void> entityWithLocation(Object resourceId) {

		// TODO 07: Set the Location header on a Response to the location of
		//          the resource and return it.
		//  a. Read the Javadoc for this method above to see what the URL should be
		//  b. You will need to use ServletUriComponentsBuilder and
		//     ResponseEntity to implement this.
		//  c. Refer to the POST example in the slides for more information

		return null; // Return something other than null
	}

	/**
	 * Returns the Beneficiary with the given name for the Account with the
	 * given id.
	 */
	@GetMapping(value = "/accounts/{accountId}/beneficiaries/{beneficiaryName}")
	public @ResponseBody Beneficiary getBeneficiary(@PathVariable("accountId") int accountId,
			@PathVariable("beneficiaryName") String beneficiaryName) {
		return retrieveAccount(accountId).getBeneficiary(beneficiaryName);
	}

	/**
	 * Adds a Beneficiary with the given name to the Account with the given id,
	 * setting its URL as the Location header on the response.
	 */
	// TODO 11: Complete this method. Add annotations to:
	//   a. Respond to a POST /accounts/{accountId}/beneficiaries
	//   b. Extract a beneficiary name from the incoming request
	//   c. Indicate a "201 Created" status
	public ResponseEntity<Void> addBeneficiary(long accountId, String beneficiaryName) {
		accountManager.addBeneficiary(accountId, beneficiaryName);

		// TODO 12: Create a ResponseEntity containing the location of the newly
		// created beneficiary.
		//  a. Look at the mapping for getBeneficiary() above to see what the URL
		//     should be.  What are we using to identify the Beneficiary?
		//  b. Use the entityWithLocation method - like we did for createAccount().
		
		return null;  // TODO 12: Modify this to return something 
	}

	/**
	 * Removes the Beneficiary with the given name from the Account with the
	 * given id.
	 */
	// TODO 13: Complete this method by adding the appropriate annotations to:
	//  a. Respond to a DELETE to /accounts/{accountId}/beneficiaries/{beneficiaryName}
	//  b. Indicate a "204 No Content" status
	public void removeBeneficiary(long accountId, String beneficiaryName) {
		Account account = accountManager.getAccount(accountId);
		Beneficiary b = account.getBeneficiary(beneficiaryName);

		// We ought to reset the allocation percentages, but for now we won't
		// bother. If we are removing the only beneficiary or the beneficiary
		// has an allocation of zero we don't need to worry. Otherwise, throw an
		// exception.
		if (account.getBeneficiaries().size() != 1 && (!b.getAllocationPercentage().equals(Percentage.zero()))) {
			// The solution has the missing logic, if you are interested.
			throw new RuntimeException("Logic to rebalance Beneficiaries not defined.");
		}

		accountManager.removeBeneficiary(accountId, beneficiaryName, new HashMap<String, Percentage>());
	}

	/**
	 * Maps IllegalArgumentExceptions to a 404 Not Found HTTP status code.
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ IllegalArgumentException.class })
	public void handleNotFound(Exception ex) {
		logger.error("Exception is: ", ex);
		// just return empty 404
	}

	// TODO 18 (BONUS): Add a new exception-handling method that maps
	// DataIntegrityViolationExceptions to a 409 Conflict status code.
	// Use the handleNotFound method above for guidance and/or look at
	// the Advanced materials in the slides.
	
	/**
	 * Finds the Account with the given id, throwing an IllegalArgumentException
	 * if there is no such Account.
	 */
	private Account retrieveAccount(long accountId) throws IllegalArgumentException {
		Account account = accountManager.getAccount(accountId);
		if (account == null) {
			throw new IllegalArgumentException("No such account with id " + accountId);
		}
		return account;
	}

}
