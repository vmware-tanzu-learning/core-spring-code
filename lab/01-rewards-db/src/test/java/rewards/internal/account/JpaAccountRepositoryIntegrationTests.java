package rewards.internal.account;

import config.AppConfig;
import config.DbConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Integration test for the JPA based account repository implementation.
 * Verifies that the JpaAccountRepository works with its underlying components
 * and that Spring is configuring things properly.
 */
@ActiveProfiles("jpa")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class, DbConfig.class })
public class JpaAccountRepositoryIntegrationTests extends AbstractAccountRepositoryTests {

	@Test
	@Override
	public void testProfile() {
		assertTrue(accountRepository.getInfo().equals(JpaAccountRepository.INFO), "JPA expected but found " + accountRepository.getInfo());
	}

}
