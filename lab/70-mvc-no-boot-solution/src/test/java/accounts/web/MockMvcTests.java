package accounts.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import config.MvcConfig;
import config.RootConfig;

/**
 * Tests using Spring's MockMVC framework. This drives an MVC application in a
 * test, as if it was running in a container, so far more checks are possible
 * than with the simple {@link AccountControllerTests}.
 */
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
@ContextConfiguration(classes = { MvcConfig.class, RootConfig.class })
@AutoConfigureMockMvc
@ActiveProfiles("jpa")
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
	public void getAccountsTest() throws Exception {
		this.mockMvc
				.perform(get("/accountList") //
						.accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
				.andExpect(status().isOk()) //
				.andExpect(model().size(1)) //
				.andExpect(model().attributeExists("accounts")) //
				.andExpect(forwardedUrl("/WEB-INF/views/accountList.jsp"));
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
	 * forwarding to "/WEB-INF/views/accountDetails.jsp" (note that this is
	 * always the full path to the JSP, even when using a View Resolver).
	 * 
	 * @throws Exception
	 *             If anything fails.
	 */
	@Test
	public void getAccountTest() throws Exception {
		this.mockMvc
				.perform(get("/accountDetails") //
						.param("entityId", "0") //
						.accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
				.andExpect(status().isOk()) //
				.andExpect(model().size(1)) //
				.andExpect(model().attributeExists("account")) //
				.andExpect(forwardedUrl("/WEB-INF/views/accountDetails.jsp"));
	}

}
