package microservices.registration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test to check if the configuration for the {@link RegistrationServer} is
 * correct.
 */
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RegistrationServer.class)
public class RegistrationServerConfigurationTests {

	@Test
	public void contextLoads() {
		// Nothing to do - just want to force the configuration to load
	}

}
