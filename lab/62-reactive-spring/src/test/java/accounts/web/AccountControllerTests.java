package accounts.web;

import accounts.internal.StubAccountManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import rewards.internal.account.Account;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A JUnit test case testing the AccountController. 
 */
public class AccountControllerTests {

	private AccountController controller;

	@BeforeEach
	public void setUp() throws Exception {
		controller = new AccountController(new StubAccountManager());
	}

	@Test
	public void testHandleDetailsRequest() {
		ExtendedModelMap model = new ExtendedModelMap();
		String viewName = controller.accountDetails(0, model);
		Account account = (Account) model.get("account");
		assertEquals("accountDetails", viewName);
		assertNotNull(account);
		assertEquals(Long.valueOf(0), account.getEntityId());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testHandleListRequest() {
		ExtendedModelMap model = new ExtendedModelMap();
		String viewName = controller.accountList(model);
		List<Account> accounts = (List<Account>) model.get("accounts");
		assertEquals("accountList", viewName);
		assertNotNull(accounts);
		assertEquals(1, accounts.size());
		assertEquals(Long.valueOf(0), accounts.get(0).getEntityId());
	}
}
