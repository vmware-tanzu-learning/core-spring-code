package tests.reactive;

import ch.qos.logback.classic.Level;
import common.util.ThreadUtils;
import org.junit.jupiter.api.*;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rewards.internal.account.Account;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Reactive tests comparing RestTemplate with WebClient.
 */
// TODO-02: To work, we need a REST server to talk to.
// * This project contains the Accounts REST server.
// * Run this project as a Spring Boot App
// * http://localhost:8080/accounts should give you a list of accounts in JSON

public class ReactiveWebClientTests {

	public static final String SERVER_URL = "http://localhost:8080";

	public static final int TOTAL_FETCHES = 200;
	public static final int TOTAL_ACCOUNTS = 21;
	public static final int TEST_ACCOUNT_ID = 0;

	private static final Logger logger = Logger.getLogger("tests.reactive");

	private static boolean hasServer = true;

	/**
	 * Check there is server to talk to.
	 */
	@BeforeAll
	public static void checkForServer() {
		try {
			Socket sock = new Socket("localhost", 8080);
			sock.close();
		} catch (Exception e) {
			logger.severe("Unable to connect to server - is it running?");

			// Give up on test
			logger.info("Tests skipped. Application terminating");
			hasServer = false;
		}
	}

	/**
	 * Reduce excess logging to make test output easier to read.
	 */
	@BeforeEach
	public void setLoggingLevels() {
		// Don't run tests if there is no server, but don't fail (allows 'mvn test' to
		// still consider the test as a success).
		Assumptions.assumeTrue(hasServer);

		// The underlying Reactor and Netty libraries are too chatty (DEBUG logging by
		// default) so lets turn them down.
		configureLogger("io.netty", "info");
		configureLogger("reactor", "info");

		// Enable logging in Account so we can see the thread being used
		configureLogger("rewards.internal.account.Account", "info");
	}

	/**
	 * Fetch an Account using RestTemplate - note that the thread running this test
	 * is the same as the thread used by the Account constructor.
	 * <p>
	 * RestTemplate runs in the SAME thread SYNCHRONOUSLY.
	 */
	@Test
	@Disabled
	public void test1RestTemplate() {
		identifyTest("REST Template Test");
		long id = TEST_ACCOUNT_ID;
		Account account = null;

		// TODO-03: Create a RestTemplate and use it to fetch an account.
		// * The URL you need is SERVER_URL + "/accounts/{id}" and id is defined above.
		// * Remove @Disabled from the method
		// Run the test, make sure it passes
		// Again check the log output - note the Account creation is logged by the same
		// thread

		logger.info("Account: " + account);
		assertEquals((Long) id, account.getEntityId());
		assertEquals("Keith and Keri Donald", account.getName());
		assertEquals(2, account.getBeneficiaries().size());
	}

	/**
	 * Fetch an Account using Spring's Reactive WebClient - note that the thread
	 * running this test is NOT the same as the thread used by the Account
	 * constructor.
	 * <p>
	 * WebClient processes the HTTP response in a DIFFERENT thread ASYNCHRONOUSLY.
	 */
	@Test
	@Disabled
	public void test2Webclient() {
		identifyTest("Web Client Mono Test");
		long id = TEST_ACCOUNT_ID;
		Account account = null;

		// TODO-04: Create a WebClient for SERVER_URL and use it to fetch an account.
		// * The uri() method will take "/accounts/{id}" and id is defined above.
		// * Accept JSON format data as the media-type
		// * Retrieve the data and convert the body of the response to a Mono<Account>
		// * Refer to your Student Handout PDF for help
		// Then block until the account is available
		// Remove @Disabled from the method
		// Run the test, make sure it passes
		// Again check the log output - note the Account creation is logged by a
		// DIFFERENT thread

		logger.info("Account: " + account);
		assertEquals((Long) id, account.getEntityId());
		assertEquals("Keith and Keri Donald", account.getName());
		assertEquals(2, account.getBeneficiaries().size());
	}

	/**
	 * Fetch all Accounts using Spring's Reactive WebClient - note that the thread
	 * running this test is NOT the same as the thread used by the Account
	 * constructor.
	 * <p>
	 * WebClient processes the HTTP responses in DIFFERENT threads ASYNCHRONOUSLY.
	 */
	@Test
	@Disabled
	public void test3Webclient() {
		identifyTest("Web Client Flux Test");

		// TODO-05: Create a WebClient for SERVER_URL and use it to fetch all accounts.
		// * The uri() method will take "/accounts".
		// * Accept JSON format data as the media-type
		// * Retrieve the data and convert the response bodies to a flux of Accounts
		// * Assign the flux to result.
		// Refer to your Student Handout PDF for help
		Flux<Account> result = null; // change this

		// We will count the number of responses (Flux items)
		final AtomicInteger counter = new AtomicInteger(0);

		// TODO-06: Process all the items in the Flux by counting each one using
		// counter.incrementAndGet().
		// * Don't forget to subscribe
		// * Pass the counting code as a lambda to subscribe()
		// * Remove @Disabled from the method
		// * Run the test, make sure it passes
		// Again check the log output - note all accounts are logged by different
		// threads

		ThreadUtils.delay(500); // Wait for the flux to catch up
		assertEquals(21, counter.get());
	}

	/**
	 * Compare RestTemplate and WebClient
	 * <p>
	 * TODO-09: Remove the @Disabled annotation below and run the test. The
	 * RestTemplate is bigger - no multi-threading overhead.
	 * <p>
	 * TODO-10: Go to AccountController - look for TO DO 10
	 * <p>
	 * TODO-11: Run this test again. Now the WebClient should be about 10x faster.
	 * When the system you are making requests for is slow, the WebClient is faster
	 * because it runs multiple requests in parallel. The RestTemplate has to wait
	 * for each to finish because it is running sequentially,
	 * <p>
	 * Congratulations, you have finished the lab.
	 */
	@Test
	@Disabled
	public void test4PerformanceTest() {
		identifyTest("Performance Comparison");

		// For this test, suppress thread logging in Account Constructor
		configureLogger("rewards.internal.account.Account", "warn");

		// RestTemplate is synchronous, simpler, but slower.
		long restTemplateDuration = fetchUsingRestTemplate();

		// The WebClient processes responses asynchronously in multiple threads to let's
		// use an AtomicLong.
		long webClientDuration = fetchUsingWebClient();

		// Report what happened. webClientDuration should be a lot smaller than
		// restTemplateDuration (approximately 1s vs 10s)
		logger.info(" REST Template Test took " + restTemplateDuration + " ms to fetch " + TOTAL_FETCHES + " accounts");
		logger.info(" Web Client Test    took " + webClientDuration + " ms to fetch " + TOTAL_FETCHES + " accounts");
	}

	/**
	 * Fetch 200 Accounts using RestTemplate.
	 * 
	 * @return Time taken to run all 200 requests.
	 */
	protected long fetchUsingRestTemplate() {
		// TODO-07: Let's compare RestTemplate and WebClient.
		// * Examine this method - it iterates 200 (TOTAL_FETCHES) times
		// * Uses RestTemplate to fetch an account
		// * Returns the total duration
		// Very straightforward SINGLE threaded code

		// First using RestTemplate
		RestTemplate template = new RestTemplate();
		long startTime = System.currentTimeMillis();
		int nAccounts = 0;
		logger.info("Using RestTemplate to fetch  " + TOTAL_FETCHES + " Accounts (You may have to wait a while)");

		// Simple code. Iterate 200 times, return how long it takes.
		for (int id = 0; id < TOTAL_FETCHES; id++) {
			Account account = template.getForObject(SERVER_URL + "/accounts/{id}", Account.class, id % TOTAL_ACCOUNTS);
			if (account != null)
				nAccounts++;
		}

		assert (nAccounts == TOTAL_FETCHES);
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * Fetch 200 Accounts using WebClient.
	 * 
	 * @return Time taken to run all 200 requests.
	 */
	protected long fetchUsingWebClient() {

		// TODO-08: Examine this method - it is more complicated. As this is not a
		// Reactive Programming course, we have written this code for you.
		// * Again it iterates 200 (TOTAL_FETCHES) times
		// * Uses WebClient to fetch an account
		// * Uses the onSuccess method of each Mono<Account> to increment nAccounts
		// * If nAccounts is 200 (all done), we record the total time taken
		// * Note that we have to remember to subscribe
		// Much more complicated MULTI threaded code

		// Repeat using Web Client
		final AtomicInteger nAccounts = new AtomicInteger(0);
		final AtomicLong webClientDuration = new AtomicLong(0);

		final long startTime = System.currentTimeMillis();
		WebClient client = WebClient.create(SERVER_URL);
		logger.info("Using WebClient to fetch  " + TOTAL_FETCHES + " Accounts");

		// Iterate 200 times. Fetch an account by id each time.
		// When the account is returned, onSuccess increments the counter
		// If the counter has reached 200, stop the clock, record the total time take
		for (int id = 0; id < TOTAL_FETCHES; id++) {
			Mono<Account> result = client.get() //
					.uri("/accounts/{id}", id % TOTAL_ACCOUNTS).accept(MediaType.APPLICATION_JSON) //
					.retrieve() //
					.bodyToMono(Account.class);
			result.doOnSuccess(a -> {
				// For each item returned
				if (nAccounts.incrementAndGet() == TOTAL_FETCHES)
					webClientDuration.set(System.currentTimeMillis() - startTime);
			}).doOnError(e -> {
				System.out.println(e.getMessage());
			}).subscribe();
		}

		while (webClientDuration.get() == 0)
			ThreadUtils.delay(50); // Wait a short while

		return webClientDuration.get();
	}

	/**
	 * These tests are not using Spring Boot (no needO), so we have to manage
	 * logging ourselves.
	 * 
	 * @param loggerName
	 *            Name of a logger.
	 */
	protected static void configureLogger(String loggerName, String level) {
		// SLF4J uses Logback underneath
		ch.qos.logback.classic.Logger logger1 = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(loggerName);
		logger1.setLevel(Level.toLevel(level));
	}

	/**
	 * To make test output easier to read, identify each test.
	 * 
	 * @param testName
	 *            Identifies the test about to run.
	 */
	private void identifyTest(String testName) {
		System.out.println();
		System.out.println("*** TEST " + testName + " ***");
		ThreadUtils.logThread(this, "");
	}

}
