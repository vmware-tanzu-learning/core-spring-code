package rewards;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rewards.CaptureSystemOutput.OutputCapture;
import rewards.internal.account.AccountRepository;
import rewards.internal.aspects.DBExceptionHandlingAspect;
import rewards.internal.exception.RewardDataAccessException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DbExceptionTestConfig.class })
@EnableAutoConfiguration
public class DBExceptionHandlingAspectTests {

	@Autowired
	AccountRepository repository;

	@Test
	@CaptureSystemOutput
	public void testReportException(OutputCapture capture) {
		assertThrows(RewardDataAccessException.class, () -> {
			repository.findByCreditCard("1234123412341234");
		});

		// The error message should have been logged to the console as a warning
		assertThat(capture.toString(), containsString(DBExceptionHandlingAspect.EMAIL_FAILURE_MSG));
	}

}
