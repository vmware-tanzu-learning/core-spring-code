package accounts;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import rewards.internal.account.Account;

import common.money.Percentage;

/**
 * An implementation of AccountManager that fetches account information from a
 * microservice. Only the first two methods are implemented to keep the lab
 * shorter.
 */
public class RemoteAccountManager implements AccountManager {

	// TODO-13: We will need to make RESTful calls to the Accounts Microservice
	//          How will we do that? Use @Autowired to get Spring to inject
	//          what we need. You will need a second annotation too.
    

    // The URL passed via the constructor - see AccountsWebApplication to
    // see what was passed.
	protected String serviceUrl;

	/**
	 * Create an instance.
	 * 
	 * @param serviceUrl
	 *            The URL needed to access the microservice using REST.
	 */
	public RemoteAccountManager(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}

	@Override
	public String getInfo() {
		// Implementation info for debugging purposes
		return "remote";
	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO-14: Make a RESTful call to fetch accounts
		Account[] accounts = null;
		return Arrays.asList(accounts);
	}

	@Override
	public Account getAccount(Long id) {
		// TODO-15: Make a RESTful call to fetch an account by its id
		return null;
	}

	// Ignore the remaining methods to keep lab shorter.

	@Override
	public Account save(Account account) {
		// DO NOT MODIFY THIS CODE
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(Account account) {
		// DO NOT MODIFY THIS CODE
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBeneficiaryAllocationPercentages(Long accountId,
			Map<String, Percentage> allocationPercentages) {
		// DO NOT MODIFY THIS CODE
		throw new UnsupportedOperationException();
	}

	@Override
	public void addBeneficiary(Long accountId, String beneficiaryName) {
		// DO NOT MODIFY THIS CODE
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeBeneficiary(Long accountId, String beneficiaryName,
			Map<String, Percentage> allocationPercentages) {
		// DO NOT MODIFY THIS CODE
		throw new UnsupportedOperationException();
	}

}
