package microservices.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import rewards.internal.account.Account;
import common.money.Percentage;

public class AccountsMicroserviceClientTests {

	/**
	 * Server URL ending with the servlet mapping on which the application can
	 * be reached.
	 */
	private static final String BASE_URL = "http://localhost:2222";

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void listAccounts() {
		String url = BASE_URL + "/accounts";
		// We have to use Account[] instead of List<Account>, or Jackson won't
		// know what type to unmarshal to. Also non-Java clients have different
		// collection representations, so returning arrays is more generic.
		Account[] accounts = restTemplate.getForObject(url, Account[].class);
		assertTrue(accounts.length >= 21);
		assertEquals("Keith and Keri Donald", accounts[0].getName());
		assertEquals(2, accounts[0].getBeneficiaries().size());
		assertEquals(Percentage.valueOf("50%"),
				accounts[0].getBeneficiary("Annabelle")
						.getAllocationPercentage());
	}

	@Test
	public void getAccount() {
		String url = BASE_URL + "/accounts/{accountId}";
		Account account = restTemplate.getForObject(url, Account.class, 0);
		assertEquals("Keith and Keri Donald", account.getName());
		assertEquals(2, account.getBeneficiaries().size());
		assertEquals(Percentage.valueOf("50%"),
				account.getBeneficiary("Annabelle").getAllocationPercentage());
	}

	@Test
	public void getAccountFail() {
		String url = BASE_URL + "/accounts/{accountId}";
		try {
			restTemplate.getForObject(url, Account.class, 2000);
			Assert.fail("Invalid account id should have thrown an exception");
		} catch (Exception e) {
			System.out.println(e.getClass());
			; // 404 Expected
		}
	}

}
