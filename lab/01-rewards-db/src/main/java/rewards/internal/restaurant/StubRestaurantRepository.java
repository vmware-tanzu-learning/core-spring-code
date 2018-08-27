package rewards.internal.restaurant;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ObjectRetrievalFailureException;

import rewards.Dining;
import rewards.internal.account.Account;

import common.money.Percentage;

/**
 * A dummy restaurant repository implementation. Has a single restaurant "Apple Bees" with a 8% benefit availability
 * percentage that's always available.
 * 
 * Stubs facilitate unit testing. An object needing a RestaurantRepository can work with this stub and not have to bring
 * in expensive and/or complex dependencies such as a Database. Simple unit tests can then verify object behavior by
 * considering the state of this stub.
 */
public class StubRestaurantRepository implements RestaurantRepository {

	public static final String TYPE = "Stub";

	private Map<String, Restaurant> restaurantsByMerchantNumber = new HashMap<String, Restaurant>();

	public StubRestaurantRepository() {
		Restaurant restaurant = new Restaurant("1234567890", "Apple Bees");
		restaurant.setBenefitPercentage(Percentage.valueOf("8%"));
		restaurant.setBenefitAvailabilityPolicy(new AlwaysReturnsTrue());
		restaurantsByMerchantNumber.put(restaurant.getNumber(), restaurant);
	}

	@Override
	public String getInfo() {
		return TYPE;
	}

	@Override
	public Restaurant findByMerchantNumber(String merchantNumber) {
		Restaurant restaurant = (Restaurant) restaurantsByMerchantNumber.get(merchantNumber);
		if (restaurant == null) {
			throw new ObjectRetrievalFailureException(Restaurant.class, merchantNumber);
		}
		return restaurant;
	}

	@Override
	public Long getRestaurantCount() {
		return 1L;
	}

	/**
	 * A simple "dummy" benefit availability policy that always returns true. Only useful for testing--a real
	 * availability policy might consider many factors such as the day of week of the dining, or the account's reward
	 * history for the current month.
	 */
	private static class AlwaysReturnsTrue implements BenefitAvailabilityPolicy {
		public boolean isBenefitAvailableFor(Account account, Dining dining) {
			return true;
		}
	}
}