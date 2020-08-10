package accounts.web;

import accounts.AccountManager;
import common.money.Percentage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

import java.util.HashMap;
import java.util.List;

/**
 * A controller handling requests for CRUD operations on Accounts and their
 * Beneficiaries.
 */
@RestController
public class AccountController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

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
	// TODO-02: Review the code that performs the following
	// a. Respond to GET /accounts
    // b. Return a List<Account> to be converted to the response body
	// - Access http://localhost:8080/accounts using a browser or curl
	//   and verify that you see the list of accounts in JSON format.
	@GetMapping(value = "/accounts")
	public List<Account> accountSummary() {
		return accountManager.getAllAccounts();
	}

	/**
	 * Provide the details of an account with the given id.
	 */
	// TODO-04: Review the code that performs the following
	// a. Respond to GET /accounts/{accountId}
    // b. Return an Account to be converted to the response body
	// - Access http://localhost:8080/accounts/0 using a browser or curl
	//   and verify that you see the account detail in JSON format
	@GetMapping(value = "/accounts/{id}")
	public Account accountDetails(@PathVariable int id) {
		return retrieveAccount(id);
	}

	/**
	 * Creates a new Account, setting its URL as the Location header on the
	 * response.
	 */
	// TODO-06: Complete this method. Add annotations to:
	// a. Respond to POST /accounts requests
    // b. Use a proper annotation for creating an Account object from the request
	public ResponseEntity<Void> createAccount(Account newAccount) {
		// Saving the account also sets its entity Id
		Account account = accountManager.save(newAccount);

		// Return a ResponseEntity - it will be used to build the
		// HttpServletResponse.
		return entityWithLocation(account.getEntityId());
	}

	/**
	 * Return a response with the location of the new resource. 
	 *
	 * Suppose we have just received an incoming URL of, say,
	 *   http://localhost:8080/accounts and resourceId is "1111".
	 * Then the URL of the new resource will be
	 *   http://localhost:8080/accounts/1111.
	 */
	private ResponseEntity<Void> entityWithLocation(Object resourceId) {

		// TODO-07: Set the 'location' header on a Response to URI of
		//          the newly created resource and return it.
		// a. You will need to use 'ServletUriComponentsBuilder' and
		//     'ResponseEntity' to implement this - Use ResponseEntity.created(..)
		// b. Refer to the POST example in the slides for more information

		return null; // Return something other than null
	}

	/**
	 * Returns the Beneficiary with the given name for the Account with the
	 * given id.
	 */
	@GetMapping(value = "/accounts/{accountId}/beneficiaries/{beneficiaryName}")
	public Beneficiary getBeneficiary(@PathVariable("accountId") int accountId,
			@PathVariable("beneficiaryName") String beneficiaryName) {
		return retrieveAccount(accountId).getBeneficiary(beneficiaryName);
	}

	/**
	 * Adds a Beneficiary with the given name to the Account with the given id,
	 * setting its URL as the Location header on the response.
	 */
	// TODO-10: Complete this method. Add annotations to:
	// a. Respond to a POST /accounts/{accountId}/beneficiaries
	// b. Extract a beneficiary name from the incoming request
	// c. Indicate a "201 Created" status
	public ResponseEntity<Void> addBeneficiary(long accountId, String beneficiaryName) {
		
		// TODO-11: Create a ResponseEntity containing the location of the newly
		// created beneficiary.
		// a. Use accountManager's addBeneficiary method to add a beneficiary to an account
		// b. Use the entityWithLocation method - like we did for createAccount().
		
		return null;  // Modify this to return something
	}

	/**
	 * Removes the Beneficiary with the given name from the Account with the
	 * given id.
	 */
	// TODO-12: Complete this method by adding the appropriate annotations to:
	// a. Respond to a DELETE to /accounts/{accountId}/beneficiaries/{beneficiaryName}
	// b. Indicate a "204 No Content" status
	public void removeBeneficiary(long accountId, String beneficiaryName) {
		Account account = accountManager.getAccount(accountId);
		if (account == null) {
			throw new IllegalArgumentException("No such account with id " + accountId);
		}
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

	// TODO-17 (Optional): Add a new exception-handling method
	// - It should map DataIntegrityViolationException to a 409 Conflict status code.
	// - Use the handleNotFound method above for guidance.
	// - Consult the lab document for further instruction
	
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
