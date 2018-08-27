package accounts.internal;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import config.AppConfig;
import config.DbConfig;

/**
 * Spring-driven integration test for the JPA-based account manager
 * implementation. Verifies that the JpaAccountManager works with its underlying
 * components.
 */
@ActiveProfiles("jpa")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class, DbConfig.class })
public class JpaAccountManagerIntegrationTests extends AbstractDatabaseAccountManagerTests {

	@Test
	@Override
	public void testProfile() {
		assertTrue("JPA expected but found " + accountManager.getInfo(), accountManager.getInfo().equals("JPA"));
	}

}
