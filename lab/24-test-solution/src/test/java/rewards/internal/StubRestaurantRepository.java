package rewards.internal;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import common.money.Percentage;
import rewards.internal.restaurant.Restaurant;
import rewards.internal.restaurant.RestaurantRepository;

/**
 * A dummy restaurant repository implementation. Has a single restaurant
 * "Apple Bees" with a 8% benefit availability percentage that's always
 * available.
 * 
 * Stubs facilitate unit testing. An object needing a RestaurantRepository can
 * work with this stub and not have to bring in expensive and/or complex
 * dependencies such as a Database. Simple unit tests can then verify object
 * behavior by considering the state of this stub.
 */
@Profile("stub")
@Repository("restaurantRepository")
public class StubRestaurantRepository implements RestaurantRepository {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Restaurant> restaurantsByMerchantNumber = new HashMap<String, Restaurant>();

	/**
	 * Creates a single test restaurant with an 8% benefit policy. Also logs
	 * creation so we know which repository we are using.
	 */
	public StubRestaurantRepository() {
		logger.info("Creating " + getClass().getSimpleName());
		Restaurant restaurant = new Restaurant("1234567890", "Apple Bees");
		restaurant.setBenefitPercentage(Percentage.valueOf("8%"));
		restaurantsByMerchantNumber.put(restaurant.getNumber(), restaurant);
	}

	public Restaurant findByMerchantNumber(String merchantNumber) {
		Restaurant restaurant = (Restaurant) restaurantsByMerchantNumber
				.get(merchantNumber);
		if (restaurant == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return restaurant;
	}
}