package accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import rewards.internal.account.Account;
import accounts.internal.StubAccountManager;

/**
 * A JUnit test case testing the AccountController.
 */
public class AccountControllerTests {

	private AccountController controller;

	@Before
	public void setUp() throws Exception {
		controller = new AccountController(new StubAccountManager());
	}

	@Test
	public void testHandleDetailsRequest() {
		Model model = new ExtendedModelMap();
		String viewName = controller.accountDetails(0L, model);
		assertNotNull(viewName);
		assertEquals("accountDetails", viewName);

		Map<String, Object> modelMap = model.asMap();
		assertEquals(1, modelMap.size());
		Account account = (Account) modelMap.get("account");
		assertNotNull(account);
		assertEquals(Long.valueOf(0), account.getEntityId());
	}

	@Test
	public void testHandleSummaryRequest() {
		Model model = new ExtendedModelMap();
		String viewName = controller.accountList(model);
		assertNotNull(viewName);
		assertEquals("accountList", viewName);

		Map<String, Object> modelMap = model.asMap();
		assertEquals(1, modelMap.size());

		@SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>) modelMap.get("accounts");
		assertNotNull(accounts);
		assertEquals(1, accounts.size());
		assertEquals(Long.valueOf(0), accounts.get(0).getEntityId());
	}

}
