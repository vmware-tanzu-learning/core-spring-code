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

        // TODO-12: (Optional) Validate our AOP is working.
        //
        // An error message should now be logged to the console as a warning
        //
        // Save all your work and run this test - it should pass with a warning
        // message on the console AND the console output assertion (below)
        // should succeed.

        if (TestConstants.CHECK_CONSOLE_OUTPUT) {
            assertThat(capture.toString(), containsString(DBExceptionHandlingAspect.EMAIL_FAILURE_MSG));
        }
    }
}