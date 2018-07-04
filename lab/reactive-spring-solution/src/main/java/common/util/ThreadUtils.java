package common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convenience class.
 */
public class ThreadUtils {

	/**
	 * Make a thread sleep for the specified period, or until interrupted
	 * 
	 * @param millis
	 *            Time to sleep in ms.
	 */
	public static void delay(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Log the current thread using the caller's Logger.
	 * 
	 * @param caller
	 * @param msg
	 */
	public static void logThread(Object caller, String msg) {
		Logger logger = LoggerFactory.getLogger(caller.getClass());
		logger.info(msg + " Current thread = " + Thread.currentThread().getName());
	}
}
