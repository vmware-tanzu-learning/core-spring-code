package rewards.internal.account;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.AppConfig;
import config.DbConfig;

/**
 * Integration test for the JPA based account repository implementation.
 * Verifies that the JpaAccountRepository works with its underlying components
 * and that Spring is configuring things properly.
 */
@ActiveProfiles("jpa")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class,DbConfig.class})
public class JpaAccountRepositoryIntegrationTests extends
		AbstractAccountRepositoryTests {

	@Test
	@Override
	public void testProfile() {
		Assert.assertTrue(
				"JPA expected but found " + accountRepository.getInfo(),
				accountRepository.getInfo().equals(JpaAccountRepository.INFO));
	}

}
