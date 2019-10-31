package rewards.internal.account;

import common.money.MonetaryAmount;
import common.money.Percentage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


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
		assertNotNull(account, "account should never be null");
		assertEquals(Long.valueOf(0), account.getEntityId(), "wrong entity id");
		assertEquals("123456789", account.getNumber(), "wrong account number");
		assertEquals("Keith and Keri Donald", account.getName(), "wrong name");
		assertEquals(2, account
				.getBeneficiaries().size(), "wrong beneficiary collection size");

		Beneficiary b1 = account.getBeneficiary("Annabelle");
		assertNotNull(b1, "Annabelle should be a beneficiary");
		assertEquals(MonetaryAmount.valueOf("0.00"),
				b1.getSavings(), "wrong savings");
		assertEquals(Percentage.valueOf("50%"),
				b1.getAllocationPercentage(), "wrong allocation percentage");

		Beneficiary b2 = account.getBeneficiary("Corgan");
		assertNotNull(b2, "Corgan should be a beneficiary");
		assertEquals(MonetaryAmount.valueOf("0.00"),
				b2.getSavings(), "wrong savings");
		assertEquals(Percentage.valueOf("50%"),
				b2.getAllocationPercentage(), "wrong allocation percentage");
	}
}
