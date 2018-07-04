package rewards;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rewards.CaptureSystemOutput.OutputCapture;
import rewards.internal.account.AccountRepository;
import rewards.internal.aspects.DBExceptionHandlingAspect;
import rewards.internal.exception.RewardDataAccessException;


@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DbExceptionTestConfig.class })
public class DBExceptionHandlingAspectTests {

	@Autowired
	AccountRepository repository;

	@Test
	@CaptureSystemOutput
	public void testReportException(OutputCapture capture) {
		try {
			repository.findByCreditCard("1234123412341234");
			fail("RewardDataAccessException expected");
		} catch (Exception e) {
			System.out.println("Exception occured...... " + e);
			assertTrue(e instanceof RewardDataAccessException);
		}

		// The error message should have been logged to the console as a warning
		assertThat(capture.toString(), containsString(DBExceptionHandlingAspect.EMAIL_FAILURE_MSG));
	}

}
