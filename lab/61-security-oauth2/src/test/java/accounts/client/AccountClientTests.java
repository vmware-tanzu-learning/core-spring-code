package accounts.client;

import accounts.client.AccountClientTests.TestClientConfig;
import common.money.Percentage;
import config.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO-13: Review Client OAuth2 configuration in "client-oauth2.properties" file.
 * Note the @TestPropertySource below.
 *
 * TODO-14: Run these tests. They should succeed.
 * (If you are using Gradle, remove test exclude statement
 *  from the build.gradle before running these test.)
 *
 * - In this scenario, AccountClientTests IS the OAuth2 client.
 * - Read the rest of this Javadoc for more information.
 *
 * This is basically the same test-client as the REST lab used.
 * But it uses OAuth2 validation because its RestTemplate is actually a
 * OAuth2RestTemplate.
 *
 * Note 1: This client uses its own OAuth2 security so security
 * auto-configuration is disabled.
 *
 * Note 2: This is not a Web application and we do not want to pick up
 * any additional configuration so we specify our own configuration class to
 * initialize from. This sets this application up as an OAuth2 client and
 * creates the OAuth2RestTemplate we need.
 *
 * Note 3: This test has its own Spring Boot properties defined in
 * "client-oauth2.properties".
 */
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = TestClientConfig.class)
@TestPropertySource("classpath:client-oauth2.properties")
public class AccountClientTests {

	/**
	 * When the account-server starts up and runs data.sql, 21 accounts are created.
	 */
	public static final int ORIGINAL_NUMBER_OF_ACCOUNTS = 21;

	/**
	 * OAuth2 name for this client - defined in client-oauth2.properties.
	 */
	public static final String CLIENT_NAME = Constants.ACCOUNT_TESTER_CLIENT;

	/**
	 * Spring Boot property for setting application name (in this case the client
	 * name).
	 */
	public static final String SPRING_APPLICATION_NAME_PROPERTY = "spring.application.name";

	/**
	 * Account server URL.
	 */
	private static final String BASE_URL = "http://localhost:8080";

	@Autowired
	private RestTemplate restTemplate;

	private Random random = new Random(); // Random number for new Accounts

	/**
	 * Additional configuration for this test - make this test an OAuth2 client and
	 * create an OAuth2 aware RestTemplate.
	 */
	@Configuration
	@EnableOAuth2Client
	public static class TestClientConfig {
		@Bean
		public RestTemplate restTemplate(OAuth2ProtectedResourceDetails details) {
			return new OAuth2RestTemplate(details);
		}
	}

	@BeforeEach
	public void setup(@Autowired Environment env) {
		// Sanity check - did this test pick up client-oauth2.properties?
		String appName = env.getProperty(SPRING_APPLICATION_NAME_PROPERTY);
		assertEquals(CLIENT_NAME, appName);
	}

	@Test
	public void listAccounts() {
		String url = BASE_URL + "/accounts";

		// We have to use Account[] instead of List<Account>, or Jackson won't know what
		// type to unmarshal to (because List<Account>.class doesn't parse).
		Account[] accounts = restTemplate.getForObject(url, Account[].class);

		// Number of accounts may be greater if you have run multiple tests without
		// restarting the server.
		assertTrue(accounts.length >= ORIGINAL_NUMBER_OF_ACCOUNTS);
		assertEquals("Keith and Keri Donald", accounts[0].getName());
		assertEquals(2, accounts[0].getBeneficiaries().size());
		assertEquals(Percentage.valueOf("50%"), //
				accounts[0].getBeneficiary("Annabelle").getAllocationPercentage());
	}

	@Test
	public void getAccount() {
		String url = BASE_URL + "/accounts/{accountId}";
		Account account = restTemplate.getForObject(url, Account.class, 0);
		assertEquals("Keith and Keri Donald", account.getName());
		assertEquals(2, account.getBeneficiaries().size());
		assertEquals(Percentage.valueOf("50%"), account.getBeneficiary("Annabelle").getAllocationPercentage());
	}

	@Test
	public void createAccount() throws URISyntaxException {
		String url = BASE_URL + "/accounts";
		// use a unique number to avoid conflicts
		String number = String.format("12345%4d", random.nextInt(10000));
		Account account = new Account(number, "John Doe");
		account.addBeneficiary("Jane Doe");
		URI newAccountLocation = restTemplate.postForLocation(url, account);

		Account retrievedAccount = restTemplate.getForObject(newAccountLocation, Account.class);
		assertEquals(account.getNumber(), retrievedAccount.getNumber());

		Beneficiary accountBeneficiary = account.getBeneficiaries().iterator().next();
		Beneficiary retrievedAccountBeneficiary = retrievedAccount.getBeneficiaries().iterator().next();

		assertEquals(accountBeneficiary.getName(), retrievedAccountBeneficiary.getName());
		assertNotNull(retrievedAccount.getEntityId());
	}

	@Test
	public void addAndDeleteBeneficiary() {
		// perform both add and delete to avoid issues with side effects
		String addUrl = BASE_URL + "/accounts/{accountId}/beneficiaries";
		URI newBeneficiaryLocation = restTemplate.postForLocation(addUrl, "David", 1);
		Beneficiary newBeneficiary = restTemplate.getForObject(newBeneficiaryLocation, Beneficiary.class);
		assertEquals("David", newBeneficiary.getName());

		restTemplate.delete(newBeneficiaryLocation);

		try {
			System.out.println("You SHOULD get the exception \"No such beneficiary with name 'David'\" in the server.");
			restTemplate.getForObject(newBeneficiaryLocation, Beneficiary.class);
			fail("Should have received 404 Not Found after deleting beneficiary at " + newBeneficiaryLocation);
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}
}
