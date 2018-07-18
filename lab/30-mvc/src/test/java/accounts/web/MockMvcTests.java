package accounts.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * TODO-12: EXTRA CREDIT Mock MVC Testing
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
@ActiveProfiles("jpa")
@ComponentScan({ "accounts.web", "config:" })
public class MockMvcTests {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Test a GET to /accountList (note the URL is relative to the servlet
	 * context - hence /accountList instead of /mvc/accounts/accountList). We
	 * tell the request that we will accept HTML then run the request by calling
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
		this.mockMvc //
				.perform(get("/accountList") //
						.accept(MediaType.parseMediaType("text/html;charset=UTF-8"))) //
				.andExpect(status().isOk()) //
				.andExpect(model().size(1)) //
				.andExpect(model().attributeExists("accounts"));
		
		// TODO-13: Note the use of the andExpect tests above. You will do something
		// similar in a moment.  For now, remove the @Disabled annotation above and
		// run the test. It should work.
		//
		// Right now - change the test to expect the logical view name (because we got
		// Spring Boot setup the MustachViewResolver for us).
		// Run the tests - this method should no pass.
	}

	/**
	 * Test a GET to /accountDetails (note the URL is relative to the servlet
	 * context - hence /accountDetails instead of /mvc/accounts/accountDetails).
	 * <p>
	 * We tell the request that we will accept HTML and specify the entityId
	 * parameter to be set to zero. Finally we run the request by invoking
	 * {@link MockMvc#perform(org.springframework.test.web.servlet.RequestBuilder)}.
	 * <p>
	 * We can tell MockMVC what we expect in the response: status OK, a model
	 * containing one attribute that should be called "account" and rendered by
	 * forwarding to "/WEB-INF/views/accountDetails.jsp". (note that this is
	 * always the full path to the JSP, even when using a View Resolver).
	 * 
	 * @throws Exception
	 *             If anything fails.
	 */
	
	// TODO-14: EXTRA CREDIT. Read the Javadoc above to see how this test works,
	// Then try running the tests. The getAccountTest() test (below) should fail
	// because even though you have just implemented it, the test still assumes a
	// 404 is returned.

	@Test
	public void getAccountTest() throws Exception {
		this.mockMvc
				.perform(get("/accountDetails") //
						.param("entityId", "0") //
						.accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
				// TODO-15: EXTRA CREDIT Fix this method
				// 1. Modify the line below - the URL is now valid so we no
				// longer get a 404.
				// 2. Add 3 more andExpect() methods similar to the
				// getAccountsTest() method above. Check that:
				// 2a. The model contains 1 attribute
				// 2b. That the attribute has the correct name
				// 2c. That we are using the right view: "accountDetails"
				// 3. Rerun the tests until they all pass.
				.andExpect(status().isNotFound());
	}

}
