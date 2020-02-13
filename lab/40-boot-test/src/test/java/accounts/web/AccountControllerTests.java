package accounts.web;

import accounts.internal.StubAccountManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
	public void accountDetails() {
		Account account = controller.accountDetails(0);
		assertThat(account).isNotNull();
		assertThat(account.getEntityId()).isEqualTo(Long.valueOf(0));
	}

	@Test
	public void accountSummary() {
		List<Account> accounts = controller.accountSummary();
		assertThat(accounts).isNotNull();
		assertThat(accounts.size()).isEqualTo(1);
		assertThat(accounts.get(0).getEntityId()).isEqualTo(Long.valueOf(0));
	}

	@Test
	public void createAccount() {
		Account newAccount = new Account("11223344", "Test");

		// ServletUriComponentsBuilder expects to find the HttpRequest in the
		// current thread (Spring MVC does this for you). For our test, we need
		// to add a mock request manually
		setupFakeRequest("http://localhost/accounts");

		HttpEntity<?> result = controller.createAccount(newAccount);
		assertThat(result).isNotNull();

		// See StubAccountManager.nextEntityId - initialized to 3
		assertThat(result.getHeaders().getLocation().toString()).isEqualTo("http://localhost/accounts/3");
	}

	@Test
	public void getBeneficiary() {
		Beneficiary beneficiary = controller.getBeneficiary(0, "Corgan");
		assertThat(beneficiary).isNotNull();
		assertThat(beneficiary.getEntityId()).isEqualTo(Long.valueOf(1));
	}

	@Test
	public void addBeneficiary() {

		// ServletUriComponentsBuilder expects to find the HttpRequest in the
		// current thread (Spring MVC does this for you). For our test, we need
		// to add a mock request manually
		setupFakeRequest("http://localhost/accounts/0/beneficiaries");

		HttpEntity<?> result = controller.addBeneficiary(0L, "Test2");
		assertThat(result).isNotNull();
		assertThat(result.getHeaders().getLocation().toString()).isEqualTo("http://localhost/accounts/0/beneficiaries/Test2");
	}

	@Test
	public void removeBeneficiary() {
		controller.removeBeneficiary(0L, "Corgan");
	}

	@Test
	public void removeBeneficiaryFail() {

		assertThrows(IllegalArgumentException.class, () -> {
			controller.removeBeneficiary(0L, "Fred");
		}, "No such beneficiary 'Fred', " + "IllegalArgumentException expected");
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
