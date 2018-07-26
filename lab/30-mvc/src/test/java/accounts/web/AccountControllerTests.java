package accounts.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import accounts.internal.StubAccountManager;
import rewards.internal.account.Account;

/**
 * A JUnit test case testing the AccountController.
 */
@RunWith(JUnitPlatform.class)
public class AccountControllerTests {

	private static final long expectedAccountId = StubAccountManager.TEST_ACCOUNT_ID;
	private static final String expectedAccountNumber = StubAccountManager.TEST_ACCOUNT_NUMBER;

	private AccountController controller;

	@BeforeEach
	public void setUp() throws Exception {
		controller = new AccountController(new StubAccountManager());
	}

	// TODO-07: Run this test, it should now pass.
	// Strictly speaking we should have tested the Controller before we ran the
	// application.
	@Test
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
		// TODO-08a: Call the controller to find the account with entityId 0
		//  - A new method is required - call it accountDetails().
		//  - It takes one parameter - a long entityId
		//  - It will return an Account.
		//  - This class won't compile until you modify the AccountController in TO DO 8

		// TODO-08b: Define the following assertions:
		// The account is not null
		// The account id matches expectedAccountId (see above)
		// The account number matches expectedAccountNumber  (see above)
	}

}
