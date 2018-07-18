package rewards.internal.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;

import rewards.internal.exception.RewardDataAccessException;


@Aspect	
public class DBExceptionHandlingAspect {
	
	public static final String EMAIL_FAILURE_MSG = "Failed sending an email to Mister Smith : ";
	
	private Logger logger = Logger.getLogger(getClass());


	//	TODO-10 OPTIONAL REQUIREMENT #3: Use AOP to log an exception.
	//  (Steps 10, 11 and 12 are optional, ignore if short of time)
	//
	//  (Optional): Configure this advice method to enable
	//	logging of exceptions thrown by Repository class methods.
	//	Select the advice type that seems most appropriate.
	
	public void implExceptionHandling(RewardDataAccessException e) {
		// Log a failure warning
		logger.warn(EMAIL_FAILURE_MSG + e + "\n");
	}

	//	TODO-11 (Optional): Annotate this class as a Spring-managed bean.
	//	Note that we enabled component scanning in an earlier step.

}
