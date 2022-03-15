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

	// TODO-10a: Remove the @Disabled annotation, run the test, it should pass.
	@Test
	@Disabled
	public void testHandleDetailsRequest() {
		// TODO-09a: Implement test code which calls the accountDetails() method on the controller.
		// - It will take one parameter - use "expectedAccountId" defined above
		// - It will return an Account

		// TODO-09b: Define the following assertions:
		// - The account is not null
		// - The account id matches "expectedAccountId" defined above
		// - The account number matches "expectedAccountNumber" defined above
	}

}
