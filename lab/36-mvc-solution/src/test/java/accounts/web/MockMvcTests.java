package accounts.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests using Spring's MockMVC framework. This drives an MVC application in a
 * test, as if it was running in a container, so far more checks are possible
 * than with the simple {@link AccountControllerTests}.
 */
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("jpa")
@ComponentScan({ "accounts.web", "config:" })
public class MockMvcTests {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Test a GET to /accountList.
	 * <p>
	 * We tell the request that we will accept HTML then run the request by calling
	 * {@link MockMvc#perform(org.springframework.test.web.servlet.RequestBuilder)}.
	 * <p>
	 * We can tell MockMVC what we expect in the response: status OK, a model
	 * containing one attribute that should be called "accounts" and rendered by
	 * using the "accountList" view.
	 * 
	 * @throws Exception
	 *             If anything fails.
	 */
	@Test
	public void getAccountsTest() throws Exception {
		int expectedNumberOfAccounts = 21;
		
		this.mockMvc //
				.perform(get("/accounts") //
						.accept(MediaType.parseMediaType("application/json"))) //
				.andExpect(status().isOk()) //
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.length()").value(expectedNumberOfAccounts));
	}

	/**
	 * Test a GET to /accountDetails.
	 * <p>
	 * We tell the request that we will accept HTML and specify the entityId
	 * parameter to be set to zero. Finally we run the request by invoking
	 * {@link MockMvc#perform(org.springframework.test.web.servlet.RequestBuilder)}.
	 * <p>
	 * We can tell MockMVC what we expect in the response: status OK, a model
	 * containing one attribute that should be called "account" and rendered by
	 * using the "accountDetails" view.
	 * 
	 * @throws Exception
	 *             If anything fails.
	 */
	@Test
	public void getAccountTest() throws Exception {
		final String expectedAccountNumber = "123456789";
		final String expectedAccountName = "Keith and Keri Donald";

		this.mockMvc.perform(get("/accounts/0") //
				.accept(MediaType.parseMediaType("application/json"))) //
				.andExpect(status().isOk()) //
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.number").value(expectedAccountNumber))
				.andExpect(jsonPath("$.name").value(expectedAccountName));
	}

}
