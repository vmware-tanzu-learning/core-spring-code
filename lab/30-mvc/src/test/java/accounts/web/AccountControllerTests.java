package accounts.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.List;

import org.springframework.ui.ExtendedModelMap;

import rewards.internal.account.Account;
import accounts.internal.StubAccountManager;

/**
 * A JUnit test case testing the AccountController. The AccountController has
 * two handler methods, therefore, two tests.
 */
@RunWith(JUnitPlatform.class)
public class AccountControllerTests {

	private AccountController controller;

	@BeforeEach
	public void setUp() throws Exception {
		controller = new AccountController(new StubAccountManager());
	}

	// TODO-06: The AccountController can be unit tested to ensure it returns the right model
	// data and view name.
	// Run this test now, it should pass.
	@SuppressWarnings("unchecked")
	@Test
	public void testHandleListRequest() {
		ExtendedModelMap model = new ExtendedModelMap();
		String viewName = controller.accountList(model);

		List<Account> accounts = (List<Account>) model.get("accounts");
		assertNotNull(accounts);
		assertEquals(1, accounts.size());
		assertEquals(Long.valueOf(0), accounts.get(0).getEntityId());

		// TODO-08: Change this to expect a logical view name
		// Re-run the test and make sure it passes
		//
		assertEquals("classpath:/templates/accountList.html", viewName);
	}

	// TODO-10: Add a test for the accountDetails() method of AccountController.
	// * Use the test method above for hints. Supply 0 for the ID value to
	// - retrieve.
	// * Create assertions for model contents (check the entity id and account number)
	// * Create an assertion for the view name.
	// * When complete run the test. It should pass.

}
