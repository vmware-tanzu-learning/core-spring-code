package accounts.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * TODO-13: EXTRA CREDIT Mock MVC Testing
 * <p>
 * Spring's Mock MVC Framework allows you to drive an MVC application in a test
 * as if it was running in a container so far more checks are possible than
 * using the simple {@link AccountControllerTests}. The tests have mostly been
 * written for you, follow the TO DO steps to see how it works.
 */

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ComponentScan({ "accounts.web", "config" })
public class MockMvcTests {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Test a GET to /accountList (note the URL is relative to the servlet
	 * context - hence /accountList instead of /mvc/accounts/accountList). We
	 * tell the request that we will accept JSON then run the request by calling
	 * {@link MockMvc#perform(org.springframework.test.web.servlet.RequestBuilder)}.
	 * <p>
	 * We can tell MockMVC what we expect in the response: status OK, a model
	 * containing one attribute that should be called "accounts" and rendered by
	 * forwarding to "/WEB-INF/views/accountList.jsp" (note that this is always
	 * the full path to the JSP, even when using a View Resolver).
	 * 
	 * @throws Exception
	 *             If anything fails.
	 */
	@Test
	@Disabled
	public void getAccountsTest() throws Exception {
		int expectedNumberOfAccounts = 21;
		
		this.mockMvc //
				.perform(get("/accounts") //
						.accept(MediaType.parseMediaType("application/json"))) //
				.andExpect(status().isOk()) //
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.length()").value(expectedNumberOfAccounts));
		
		// TODO-14: Note the use of the andExpect tests above. You will do something
		// similar in a moment.  For now, remove the @Disabled annotation above and
		// run the test. This test method should now pass.
	}

	/**
	 * Test a GET to /account/0 (we just need the URL, no host name or port).
	 * <p>
	 * We tell the request that we will accept JSON.
	 * <p>
	 * Finally we run the request by invoking
	 * {@link MockMvc#perform(org.springframework.test.web.servlet.RequestBuilder)}.
	 * <p>
	 * We tell MockMVC what we expect in the response:
	 * <ul>
	 * <li>Response Status OK (200)
	 * <li>A response containing JSON data representing an account
	 * <li>The account number should match  "123456789"
	 * <li>The account name should match  "Keith and Keri Donald"
	 * </ul>
	 * 
	 * @throws Exception
	 *             If anything fails.
	 */
	
	// TODO-15: EXTRA CREDIT. Read the Javadoc above to see how this test should work,
	// Get rid of @Disabled. Then try running the tests again
	// The getAccountTest() test (below) should pass but it is doing no validation.
	@Test
	@Disabled
	public void getAccountTest() throws Exception {
		final String expectedAccountNumber = "123456789";
		final String expectedAccountName = "Keith and Keri Donald";

		this.mockMvc
				.perform(get("/accounts/0") //
						.accept(MediaType.parseMediaType("application/json")))
				// TODO-16: EXTRA CREDIT Fix this method
				// 1. Add 4 andExpect() methods similar to getAccountsTest()
				//    Check that:
				// 1a. We get a 200 OK response
				// 1b. We have the right response type (JSON again)
				// 1c. That the number property of the account matches expectedAccountNumber
				//     The JSON path you need is "$.number"
				// 1d. The account name matches expectedAccountName
				// 2. Rerun the tests until they both pass.
				;
	}

}
