package tests.reactive;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;

/**
 * Demonstration of the code used in the slides.
 * <p>
 * These examples taken From Dave Syer's Spring Blog article:
 * https://spring.io/blog/2016/06/13/notes-on-reactive-programming-part-ii-writing-some-code
 */
public class SimpleReactiveTests {

	private static final Logger logger = LoggerFactory.getLogger(SimpleReactiveTests.class);

	private static final String[] TEST_DATA = { "red", "white", "blue", "orange", "green", "purple", "yellow",
			"black" };

	/**
	 * This test shows the basic use of Java Streams and Reactor Flux.
	 * <p>
	 * TODO-01: Reactive introduction: Run the test and note the logging output.
	 * Each log line starts with [thread-name] such as [main] for the main thread.
	 */
	@Test
	public void test1() {

		// Iterate over a collection using stream
		// Log output shows everything running in the main thread
		// The first item in the log output, [main], is the thread name
		System.out.println("JDK Streams - one thread");
		Arrays.asList(TEST_DATA) //
				.stream() //
				.peek(logger::info) //
				.map(String::toUpperCase) //
				.forEach(SimpleReactiveTests::logFinalValue);

		System.out.println();

		// Iterate over a collection using parallel stream
		// Log output shows that pool of threads are used
		System.out.println("JDK Streams - multiple threads");
		Arrays.asList(TEST_DATA) //
				.parallelStream() //
				.peek(logger::info) //
				.map(String::toUpperCase) //
				.forEach(SimpleReactiveTests::logFinalValue);

		System.out.println();

		// Reactive equivalent using a Reactor "Flux"
		// Log output still shows everything running in the main thread
		System.out.println("Reactor Flux - one thread");
		Flux.just(TEST_DATA) //
				.log() //
				.map(String::toUpperCase) //
				.subscribe(SimpleReactiveTests::logFinalValue);

		System.out.println();

		// Use flatMap() to create a Reactive "Mono" for each item in the event stream
		// allowing it to be processed in a different thread. The subscriber requests 3
		// threads from the internal scheduler.
		//
		// Log output now shows some processing running in DIFFERENT threads
		System.out.println("Reactor Flux - multiple threads (min 4 requested)");
		Flux.just(TEST_DATA) //
				.log() //
				.flatMap(value -> Mono.just(value.toUpperCase()).subscribeOn(Schedulers.parallel()), 4) //
				.subscribe(SimpleReactiveTests::logFinalValue);
	}

	public static void logFinalValue(String value) {
		logger.info("Final value: " + value);
	}
}
