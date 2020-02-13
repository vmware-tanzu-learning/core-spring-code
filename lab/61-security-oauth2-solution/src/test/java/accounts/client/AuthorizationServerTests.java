package accounts.client;
import org.junit.jupiter.api.Test;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorizationServerTests {

	public static final String AUTH_SERVER = "http://localhost:1111";
	public static final String ACCOUNT_SERVER = "http://localhost:8080";

	public static final String FORM_MIME_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
	public static final String AUTHORIZATION_HEADER_PROPERTY_NAME = "Authorization";
	public static final String CONTENT_TYPE_HEADER_PROPERTY_NAME = "Content-Type";
	public static final String ACCEPT_HEADER_PROPERTY_NAME = "Accept";
	public static final String APPLICATION_JSON_MINE_TYPES = "application/json, application/*+json";

	public static final String CLIENT_AUTH = // account-tester:secret
			"Basic YWNjb3VudC10ZXN0ZXI6c2VjcmV0";

	public static final String ACCOUNT_SERVER_AUTH = // account-server:secret
			"Basic YWNjb3VudC1zZXJ2ZXI6c2VjcmV0";

	public static final String GET_TOKEN_BODY = //
			"grant_type=client_credentials&response_type=token&scope=account.read";

	/**
	 * A normal RestTemplate - it has no OAuth2 knowledge.
	 */
	private RestTemplate template = new RestTemplate();

	@Test
	public void canGetToken() {
		String token = getToken();
		assertNotNull(token);
	}

	@Test
	public void canUseToken() {
		String token = getToken();
		assertNotNull(token);
		checkToken(token);
	}

	@Test
	public void canGetAccountUsingToken() {
		String token = getToken();
		assertNotNull(token);
		checkToken(token);
		getAccountUsingToken(token);
	}

	private String getToken() {
		// Performs the equivalent of this curl command:
		//
		// curl -X POST localhost:1111/oauth/token -u account-tester:secret
		// -d 'grant_type=client_credentials&response_type=token&scope=account.read'
		//
		// Accept: application/json, application/*+json
		// Authorization: Basic YWNjb3VudC10ZXN0ZXI6c2VjcmV0
		// Content-Type: application/x-www-form-urlencoded;charset=UTF-8

		RequestEntity<String> request = //
				RequestEntity.post(toURI(AUTH_SERVER + "/oauth/token"))
						.header(AUTHORIZATION_HEADER_PROPERTY_NAME, CLIENT_AUTH) //
						.header(ACCEPT_HEADER_PROPERTY_NAME, APPLICATION_JSON_MINE_TYPES)
						.header(CONTENT_TYPE_HEADER_PROPERTY_NAME, FORM_MIME_TYPE) //
						.body(GET_TOKEN_BODY);

		ResponseEntity<String> response = template.exchange(request, String.class);
		assertEquals(200, response.getStatusCodeValue());

		System.out.println(response.getBody());
		String[] bits = response.getBody().split("\"");
		String token = "";

		int i = 0;

		while (i++ < bits.length) {
			if (bits[i].equals("access_token")) {
				token = bits[i + 2];
				break;
			}
		}

		System.out.println("token = " + token);
		return token;
	}

	private void checkToken(String token) {
		// Performs the equivalent of this curl command:
		//
		// curl -X POST -u account-server:secret localhost:1111/oauth/check_token
		// -d token={{token}}
		//
		// Accept: application/json, application/*+json
		// Authorization: Basic YWNjb3VudC1zZXJ2ZXI6c2VjcmV0

		RequestEntity<String> request = //
				RequestEntity.post(toURI(AUTH_SERVER + "/oauth/check_token"))
				.header(AUTHORIZATION_HEADER_PROPERTY_NAME, ACCOUNT_SERVER_AUTH)
				.header(ACCEPT_HEADER_PROPERTY_NAME, APPLICATION_JSON_MINE_TYPES) //
				.header(CONTENT_TYPE_HEADER_PROPERTY_NAME, FORM_MIME_TYPE) //
				.body("token=" + token);
		
		ResponseEntity<String> response = template.exchange(request, String.class);
		
		System.out.println(response.getStatusCodeValue());
		assertEquals(200, response.getStatusCodeValue());
		System.out.println(response.getBody());
	}

	private void getAccountUsingToken(String token) {
		// Performs the equivalent of this curl command:
		//
		// curl -i localhost:8080/accounts/1 -H "Authorization: Bearer ..."
		// -H "Accept: application/json"
		//
		// Accept: application/json, application/*+json
		// Authorization: Bearer {{token}}

		RequestEntity<Void> request = //
				RequestEntity.get(toURI(ACCOUNT_SERVER + "/accounts/"))
						.header(AUTHORIZATION_HEADER_PROPERTY_NAME, "Bearer " + token)
						.header(ACCEPT_HEADER_PROPERTY_NAME, APPLICATION_JSON_MINE_TYPES) //
						.build();

		ResponseEntity<String> response = template.exchange(request, String.class);

		System.out.println(response.getStatusCodeValue());
		assertEquals(200, response.getStatusCodeValue());
		System.out.println(response.getBody());
		assertTrue(response.getBody().contains("Keith and Keri Donald"));
	}

	protected URI toURI(String url) {
		try {
			return new URI(url);
		} catch (URISyntaxException e) {
			fail("Unable to create URI from " + url);
			return null; // Never gets this far
		}
	}
}
