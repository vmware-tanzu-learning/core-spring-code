package rewards.internal.restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * TODO-05: We have removed the JdbcRestaurantRepository and replaced it by this
 * JpaRestaurantRepository. The implementation of findByMerchantNumber() is very
 * similar to the query you just wrote, so we have written it for you.
 */
@Repository("restaurantRepository")
public class JpaRestaurantRepository implements RestaurantRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Restaurant findByMerchantNumber(String merchantNumber) {
		String jpql = "SELECT r FROM Restaurant r where r.number = :merchantNumber";
		Restaurant restaurant = entityManager.createQuery(jpql, Restaurant.class)
				.setParameter("merchantNumber", merchantNumber).getSingleResult();
		return restaurant;
	}

}
