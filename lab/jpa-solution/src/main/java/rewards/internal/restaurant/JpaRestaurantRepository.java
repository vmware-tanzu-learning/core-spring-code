package rewards.internal.restaurant;

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
	public Restaurant findByNumber(String merchantNumber) {
		String jpql = "SELECT r FROM Restaurant r where r.number = :merchantNumber";
		Restaurant restaurant = entityManager.createQuery(jpql, Restaurant.class)
				                                    .setParameter("merchantNumber", merchantNumber)
				                                    .getSingleResult();
		return restaurant;
	}

}
