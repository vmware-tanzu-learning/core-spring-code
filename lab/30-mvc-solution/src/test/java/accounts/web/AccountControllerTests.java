package accounts.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

	private AccountController controller;

	@BeforeEach
	public void setUp() throws Exception {
		controller = new AccountController(new StubAccountManager());
	}

	@Test
	public void testHandleDetailsRequest() {
		Account account = controller.accountDetails(0);

		assertNotNull(account);
		assertEquals(StubAccountManager.TEST_ACCOUNT_ID, (long) account.getEntityId());
		assertEquals(StubAccountManager.TEST_ACCOUNT_NUMBER, account.getNumber());
	}

	@Test
	public void testHandleListRequest() {
		List<Account> accounts = controller.accountList();

		// Non-empty list containing the one and only test account
		assertNotNull(accounts);
		assertEquals(1, accounts.size());

		// Validate that account
		Account account = accounts.get(0);
		assertEquals(StubAccountManager.TEST_ACCOUNT_ID, (long) account.getEntityId());
		assertEquals(StubAccountManager.TEST_ACCOUNT_NUMBER, account.getNumber());

	}
}
