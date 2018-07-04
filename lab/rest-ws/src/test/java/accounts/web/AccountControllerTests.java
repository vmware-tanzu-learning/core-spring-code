package accounts.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
		assertEquals(Long.valueOf(0), account.getEntityId());
	}

	@Test
	public void testHandleSummaryRequest() {
		List<Account> accounts = controller.accountSummary();
		assertNotNull(accounts);
		assertEquals(1, accounts.size());
		assertEquals(Long.valueOf(0), accounts.get(0).getEntityId());
	}
	
	// TODO 19 (BONUS): Testing with a Mock HTTP request.
	// (1) Remove the @Disabled annotation and run these tests.
	// testCreateAccount() will fail with an IllegalStateException: "Could not
	// find current request via RequestContextHolder". This is because
	// ServletUriComponentsBuilder doesn't have an HTTP request to initialize
	// itself from.
	//
    // (2) To run this test successfully, we need to mock up a request.
	// Uncomment the setupFakeRequest() method call. Rerun the tests and they
	// should all pass.
    //
	// (3) Examine the Javadoc and comments in setupFakeRequest() to see how
	// it works.
	//
	// Spring's "org.springframework.mock.web" package contains many
	// other useful classes for testing Controllers like MockHttpSession
	// and MockHttpSrvletResponse.
	@Test
	@Disabled
	public void testCreateAccount() {
		Account newAccount = new Account("11223344", "Test");

		// ServletUriComponentsBuilder expects to find the HttpRequest in the
		// current thread (Spring MVC does this for you). For our test, we need
		// to add a mock request manually
		//setupFakeRequest("http://localhost/accounts");

		HttpEntity<?> result = controller.createAccount(newAccount);
		assertNotNull(result);

		// See StubAccountManager.nextEntityId - initialized to 3
		assertEquals("http://localhost/accounts/3", result.getHeaders().getLocation().toString());
	}
	
	/**
	 * Add a mocked up HttpServletRequest to Spring's internal request-context
	 * holder. Normally the DispatcherServlet does this, but we must do it
	 * manually to run our test.
	 * 
	 * @param url
	 *            The URL we are creating the fake request for.
	 */
	private void setupFakeRequest(String url) {
		String requestURI = url.substring(16); // Drop "http://localhost"

		// We can use Spring's convenient mock implementation. Defaults to
		// localhost in the URL. Since we only need the URL, we don't need
		// to setup anything else in the request.
		MockHttpServletRequest request = new MockHttpServletRequest("POST", requestURI);

		// Puts the fake request in the current thread for the
		// ServletUriComponentsBuilder to initialize itself from later.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}
}
