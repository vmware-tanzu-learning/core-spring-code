package rewards.internal.restaurant;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * Loads restaurants from a data source using JPA.
 */
@Repository("restaurantRepository")
public class JpaRestaurantRepository implements RestaurantRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Restaurant findByMerchantNumber(String merchantNumber) {
		String jpql = "SELECT r FROM Restaurant r where r.number = :merchantNumber";
		List<Restaurant> restaurants = entityManager.createQuery(jpql, Restaurant.class)
				                                    .setParameter("merchantNumber", merchantNumber)
				                                    .getResultList();
		return restaurants.get(0);
	}

}
