package tests.reactive;

import ch.qos.logback.classic.Level;
import common.util.ThreadUtils;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
	public void test1RestTemplate() {
		identifyTest("REST Template Test");
		long id = TEST_ACCOUNT_ID;

		RestTemplate template = new RestTemplate();
		Account account = template.getForObject(SERVER_URL + "/accounts/{id}", Account.class, id);

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
	public void test2Webclient() {
		identifyTest("Web Client Mono Test");
		long id = TEST_ACCOUNT_ID;

		WebClient client = WebClient.create(SERVER_URL);
		Mono<Account> result = client.get() //
				.uri("/accounts/{id}", id) //
				.accept(MediaType.APPLICATION_JSON) //
				.retrieve() //
				.bodyToMono(Account.class);

		// Wait for account to be returned
		Account account = result.block();
		logger.info("Account: " + account);
		assertEquals((Long) id, account.getEntityId());
		assertEquals("Keith and Keri Donald", account.getName());
		assertEquals(2, account.getBeneficiaries().size());

		// Same again, but this time, process asynchronously using subscribe
		Mono<Account> result2 = client.get() //
				.uri("/accounts/{id}", id) //
				.accept(MediaType.APPLICATION_JSON) //
				.retrieve() //
				.bodyToMono(Account.class);

		result2.subscribe(a -> {
			ThreadUtils.logThread(this, "Account: " + a.getName());
		});
	}

	/**
	 * Fetch an Account using Spring's Reactive WebClient - note that the thread
	 * running this test is NOT the same as the thread used by the Account
	 * constructor.
	 * <p>
	 * WebClient processes the HTTP response in a DIFFERENT thread ASYNCHRONOUSLY.
	 */
	@Test
	public void test3Webclient() {
		identifyTest("Web Client Flux Test");

		WebClient client = WebClient.create(SERVER_URL);

		Flux<Account> result2 = client.get() //
				.uri("/accounts") //
				.accept(MediaType.APPLICATION_JSON) //
				.retrieve() // Send request
				.bodyToFlux(Account.class);

		final AtomicInteger counter = new AtomicInteger(0);

		result2.subscribe(a -> {
			counter.incrementAndGet(); //
			System.out.println("  Account:" + counter + " " + a.getName());
		});

		ThreadUtils.delay(500); // Wait for the flux to finish streaming
		assertEquals(21, counter.get());
	}

	@Test
	public void test4PerformanceTest() {
		identifyTest("Performance Comparison");

		// For this test, suppress thread logging in Account Constructor
		configureLogger("rewards.internal.account.Account", "warn");

		// The WebClient processes responses asynchronously in multiple threads to let's
		// use an AtomicLong.
		long webClientDuration = fetchUsingWebClient();

		// RestTemplate is synchronous, simpler, but slower.
		long restTemplateDuration = fetchUsingRestTemplate();

		// Report what happened. webClientDuration should be a lot smaller than
		// restTemplateDuration (approximately 1s vs 10s)
		logger.info(" Web Client Test    took " + webClientDuration + " ms to fetch " + TOTAL_FETCHES + " accounts");
		logger.info(" REST Template Test took " + restTemplateDuration + " ms to fetch " + TOTAL_FETCHES + " accounts");
	}

	/**
	 * Fetch 200 Accounts using WebClient.
	 * 
	 * @return Time taken to run all 200 requests.
	 */
	protected long fetchUsingWebClient() {
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
	 * Fetch 200 Accounts using RestTemplate.
	 * 
	 * @return Time taken to run all 200 requests.
	 */
	protected long fetchUsingRestTemplate() {
		// First using RestTemplate
		RestTemplate template = new RestTemplate();
		long startTime = System.currentTimeMillis();
		int nAccounts = 0;
		logger.info("Using RestTemplate to fetch  " + TOTAL_FETCHES + " Accounts (You may have to wait a while)");

		// Much simpler code. Iterate 200 times, return how long it takes.
		for (int id = 0; id < TOTAL_FETCHES; id++) {
			Account account = template.getForObject(SERVER_URL + "/accounts/{id}", Account.class, id % TOTAL_ACCOUNTS);
			if (account != null)
				nAccounts++;
		}

		assert (nAccounts == TOTAL_FETCHES);
		return System.currentTimeMillis() - startTime;
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
