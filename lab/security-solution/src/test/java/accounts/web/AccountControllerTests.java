package accounts.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import accounts.AccountManager;
import accounts.internal.StubAccountManager;

/**
 * A JUnit test case testing the AccountController. Inherits and runs all the
 * tests in {@link AbstractAccountControllerTests} using a
 * {@link StubAccountManager} (which defines one test account).
 */
@RunWith(JUnitPlatform.class)
public class AccountControllerTests extends AbstractAccountControllerTests {

	/**
	 * Setup an {@link AccountController} using a stub {@link AccountManager}
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		manager = new StubAccountManager();
		controller = new AccountController(manager);
	}

	@Override
	protected int getNumAccountsExpected() {
		// The number of accounts defined in the stub
		return StubAccountManager.NUM_ACCOUNTS_IN_STUB;
	}
}
