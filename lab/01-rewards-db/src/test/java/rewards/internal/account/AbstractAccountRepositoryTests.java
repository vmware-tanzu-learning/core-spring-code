package rewards.internal.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import common.money.MonetaryAmount;
import common.money.Percentage;

/**
 * Unit tests for an account repository implementation.
 * <p>
 * Tests application behavior to verify the AccountRepository and the database
 * mapping of its domain objects are correct. The implementation of the
 * AccountRepository class is irrelevant to these tests and so is the testing
 * environment (stubbing, manual or Spring-driven configuration).
 */
public abstract class AbstractAccountRepositoryTests {

	@Autowired
	protected AccountRepository accountRepository;

	@Test
	public abstract void testProfile();

	@Test
	@Transactional
	public void findByCreditCard() {
		Account account = accountRepository
				.findByCreditCard("1234123412341234");

		// assert the returned account contains what you expect given the state
		// of the database
		assertNotNull("account should never be null", account);
		assertEquals("wrong entity id", Long.valueOf(0), account.getEntityId());
		assertEquals("wrong account number", "123456789", account.getNumber());
		assertEquals("wrong name", "Keith and Keri Donald", account.getName());
		assertEquals("wrong beneficiary collection size", 2, account
				.getBeneficiaries().size());

		Beneficiary b1 = account.getBeneficiary("Annabelle");
		assertNotNull("Annabelle should be a beneficiary", b1);
		assertEquals("wrong savings", MonetaryAmount.valueOf("0.00"),
				b1.getSavings());
		assertEquals("wrong allocation percentage", Percentage.valueOf("50%"),
				b1.getAllocationPercentage());

		Beneficiary b2 = account.getBeneficiary("Corgan");
		assertNotNull("Corgan should be a beneficiary", b2);
		assertEquals("wrong savings", MonetaryAmount.valueOf("0.00"),
				b2.getSavings());
		assertEquals("wrong allocation percentage", Percentage.valueOf("50%"),
				b2.getAllocationPercentage());
	}
}
