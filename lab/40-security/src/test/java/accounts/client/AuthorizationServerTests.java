package accounts.client;

import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class AuthorizationServerTests {

	public static final String AUTH_SERVER = "http://localhost:1111";
	public static final String ACCOUNT_SERVER = "http://localhost:8080";

	public static final String FORM_MIME_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
	public static final String AUTHORIZATION_HEADER_PROPERTY_NAME = "Authorization";
	public static final String CONTENT_TYPE_HEADER_PROPERTY_NAME = "Content-Type";
	public static final String ACCEPT_HEADER_PROPERTY_NAME = "Accept";
	public static final String APPLICATION_JSON_MINE_TYPES = "application/json, application/*+json";

	public static final String CLIENT_AUTH = // account-tester:secret
			"Basic YWNjb3VudC1zZXJ2ZXI6c2VjcmV0";

	public static final String ACCOUNT_SERVER_AUTH = // account-server:secret
			"Basic YWNjb3VudC1zZXJ2ZXI6c2VjcmV0";

	public static final String GET_TOKEN_BODY = //
			"grant_type=client_credentials&response_type=token&scope=account.read";

	/**
	 * A normal RestTemplate - it has no OAuth2 knowledge.
	 */
	private RestTemplate template = new RestTemplate();

	/**
	 * TODO-12b. Remove @Disabled and run this test. It should work.
	 * <p>
	 * If you can't work out why the Authorization Server is not working, in
	 * auth-server.properties uncomment the logging.level properties to enable DEBUG
	 * logging for Spring Security and Boot. Rerun the test and see if the log
	 * output helps you fix the server
	 */
	@Test
	@Disabled
	public void canGetToken() {
		String token = getToken();
		Assert.assertNotNull(token);
	}

	/**
	 * TODO-13b. Remove @Disabled and run this test. It should work.
	 * <p>
	 * TODO-14: Return to dashboard at http://localhost:1111 and click the "Recent
	 * requests" link. Can you see the OAuth URLs being used to get and check a
	 * token?  If you had to login, rerun the tests then refresh the "Recent
	 * requests" page.  The top two URLs should be the token POSTs.
	 * Or search for "oauth" string.
	 */
	@Test
	@Disabled
	public void canUseToken() {
		String token = getToken();
		Assert.assertNotNull(token);
		checkToken(token);
	}

	/**
	 * TODO-18b: Remove @Disabled and run this test. It should succeed and fetch
	 * data from the account-server.
	 */
	@Test
	@Disabled
	public void canGetAccountUsingToken() {
		String token = getToken();
		Assert.assertNotNull(token);
		checkToken(token);
		getAccountUsingToken(token);
	}

	/**
	 * TODO-12a: Review this code. It runs a request for a token running as the
	 * "account-tester" (our Client process)
	 * 
	 * @return The token returned from the Authorization Server.
	 */
	private String getToken() {
		// Performs the equivalent of this curl command:
		//
		// curl -X POST localhost:1111/oauth/token
		// -u account-tester:secret
		// -H "Accept: application/json, application/*+json"
		// -H "Content-Type: application/x-www-form-urlencoded;charset=UTF-8"
		// -d 'grant_type=client_credentials&response_type=token&scope=account.read'
		//
		// HTTP Request Headers are
		// - Accept: application/json, application/*+json
		// - Authorization: Basic YWNjb3VudC1zZXJ2ZXI6c2VjcmV0
		// - Content-Type: application/x-www-form-urlencoded;charset=UTF-8

		RequestEntity<String> request = //
				RequestEntity.post(toURI(AUTH_SERVER + "/oauth/token"))
						.header(AUTHORIZATION_HEADER_PROPERTY_NAME, CLIENT_AUTH) //
						.header(ACCEPT_HEADER_PROPERTY_NAME, APPLICATION_JSON_MINE_TYPES)
						.header(CONTENT_TYPE_HEADER_PROPERTY_NAME, FORM_MIME_TYPE) //
						.body(GET_TOKEN_BODY);

		ResponseEntity<String> response = template.exchange(request, String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());

		// The response body should be JSON containing the token
		System.out.println(response.getBody());

		String token = extractTokenFromJSON(response.getBody());

		// Show the value.
		System.out.println("token = " + token);
		return token;
	}

	/**
	 * TODO-13a: Review this code. It runs a check to validate a token, still
	 * running as the "account-tester" (our Client process).
	 * 
	 * @param token
	 *            Token previously received from Authorization Server.
	 */
	private void checkToken(String token) {
		// Performs the equivalent of this curl command:
		//
		// curl -X POST localhost:1111/oauth/check_token
		// -u account-tester:secret
		// -H "Content-Type: application/json, application/*+json"
		// -d token={{token}}
		//
		// HTTP Request Headers are
		// - Authorization: Basic YWNjb3VudC1zZXJ2ZXI6c2VjcmV0
		// - Accept: application/json, application/*+json
		// - Content-Type: application/json, application/*+json

		RequestEntity<String> request = //
				RequestEntity.post(toURI(AUTH_SERVER + "/oauth/check_token"))
						.header(AUTHORIZATION_HEADER_PROPERTY_NAME, ACCOUNT_SERVER_AUTH)
						.header(ACCEPT_HEADER_PROPERTY_NAME, APPLICATION_JSON_MINE_TYPES) //
						.header(CONTENT_TYPE_HEADER_PROPERTY_NAME, FORM_MIME_TYPE) //
						.body("token=" + token);

		ResponseEntity<String> response = template.exchange(request, String.class);
		System.out.println(response.getStatusCodeValue());

		// Return status 200 implies a valid token.
		Assert.assertEquals(200, response.getStatusCodeValue());
		System.out.println(response.getBody());
	}

	/**
	 * Using the access token, fetch data from the account-service.
	 * <p>
	 * TODO-18a: Review the code. Makes an HTTP request using the access token for
	 * Authentication.
	 * 
	 * @param token
	 *            Token from the Authorization Server.
	 */
	private void getAccountUsingToken(String token) {
		// Performs the equivalent of this curl command:
		//
		// curl -i localhost:8080/accounts/0
		// -H "Authorization: Bearer ..."
		// -H "Accept: application/json"
		//
		// HTTP Request Headers are
		// - Accept: application/json, application/*+json
		// - Authorization: Bearer {{token}}

		RequestEntity<Void> request = //
				RequestEntity.get(toURI(ACCOUNT_SERVER + "/accounts/0"))
						.header(AUTHORIZATION_HEADER_PROPERTY_NAME, "Bearer " + token)
						.header(ACCEPT_HEADER_PROPERTY_NAME, APPLICATION_JSON_MINE_TYPES) //
						.build();

		ResponseEntity<String> response = template.exchange(request, String.class);

		// Should get a valid REST response containing account 0
		System.out.println(response.getStatusCodeValue());
		Assert.assertEquals(200, response.getStatusCodeValue());
		System.out.println(response.getBody());
		Assert.assertTrue(response.getBody().contains("Keith and Keri Donald"));
	}

	/**
	 * Quick and dirty JSON parsing ...
	 * 
	 * @param body
	 *            Body of HTTP Response. Should be a JSON string containing an
	 *            access_Token.
	 * @return The token.
	 */
	private String extractTokenFromJSON(String body) {
		String[] bits = body.split("\"");

		// Find 'access_token: xxx' in the JSON data from the response.
		int i = 0;

		while (i++ < bits.length) {
			if (bits[i].equals("access_token")) {
				return bits[i + 2]; // Get the token value
			}
		}

		Assert.fail("No access token found in response");
		return null; // Never gets this far
	}

	/**
	 * Convert a String to a URI.
	 * 
	 * @param url
	 *            URL string
	 * @return Equivalent URI.
	 */
	protected URI toURI(String url) {
		try {
			return new URI(url);
		} catch (URISyntaxException e) {
			Assert.fail("Unable to create URI from " + url);
			return null; // Never gets this far
		}
	}
}
