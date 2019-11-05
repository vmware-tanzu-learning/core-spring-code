package accounts.web;

import accounts.internal.StubAccountManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import rewards.internal.account.Account;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A JUnit test case testing the AccountController.
 */
public class AccountControllerTests {

	private static final long expectedAccountId = StubAccountManager.TEST_ACCOUNT_ID;
	private static final String expectedAccountNumber = StubAccountManager.TEST_ACCOUNT_NUMBER;

	private AccountController controller;

	@BeforeEach
	public void setUp() throws Exception {
		controller = new AccountController(new StubAccountManager());
	}

	// TODO-07: Remove the @Disabled annotation, run the test, it should now pass.
	// Strictly speaking we should have tested the Controller before we ran the
	// application.
	@Test
	@Disabled
	public void testHandleListRequest() {
		List<Account> accounts = controller.accountList();

		// Non-empty list containing the one and only test account
		assertNotNull(accounts);
		assertEquals(1, accounts.size());

		// Validate that account
		Account account = accounts.get(0);
		assertEquals(expectedAccountId, (long) account.getEntityId());
		assertEquals(expectedAccountNumber, account.getNumber());
	}

	// TODO-10: Remove the @Disabled annotation, run the test, it should pass.
	@Test
	@Disabled
	public void testHandleDetailsRequest() {
		// TODO-08a: Implement test code which calls the accountDetails() method on the controller.
		//  - The accountDetails() method does not exist yet.  We will implement it in the next step.
		//  - It will take one parameter - a long entityId
		//  - It will return an Account.
		//  - This class won't compile until you modify the AccountController in TO DO 09

		// TODO-08b: Define the following assertions:
		// The account is not null
		// The account id matches expectedAccountId (see above)
		// The account number matches expectedAccountNumber  (see above)
	}

}
