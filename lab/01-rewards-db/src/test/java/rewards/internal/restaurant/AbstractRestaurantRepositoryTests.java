package rewards.internal.restaurant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import common.money.Percentage;

/**
 * Unit tests for a restaurant repository implementation.
 * <p>
 * Tests application behavior to verify the RestaurantRepository and the
 * database mapping of its domain objects are correct. The implementation of the
 * RestaurantRepository class is irrelevant to these tests and so is the testing
 * environment (stubbing, manual or Spring-driven configuration).
 */
public abstract class AbstractRestaurantRepositoryTests {

	@Autowired
	protected RestaurantRepository restaurantRepository;

	@Test
	public abstract void testProfile();

	@Test
	@Transactional
	public final void findRestaurantByMerchantNumber() {
		Restaurant restaurant = restaurantRepository
				.findByMerchantNumber("1234567890");
		assertNotNull("the restaurant should never be null", restaurant);
		assertEquals("the merchant number is wrong", "1234567890",
				restaurant.getNumber());
		assertEquals("the name is wrong", "AppleBees", restaurant.getName());
		assertEquals("the benefitPercentage is wrong",
				Percentage.valueOf("8%"), restaurant.getBenefitPercentage());
		assertEquals("the benefit availability policy is wrong",
				AlwaysAvailable.INSTANCE,
				restaurant.getBenefitAvailabilityPolicy());
	}

}
