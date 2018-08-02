package rewards.internal.restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads restaurants from a data source using JPA.
 */
public class JpaRestaurantRepository implements RestaurantRepository {

	public static final String INFO = "JPA";

	private static final Logger logger = LoggerFactory.getLogger("config");

	private EntityManager entityManager;

	public JpaRestaurantRepository() {
		logger.info("Created JpaRestaurantRepository");
	}

	/**
	 * Creates a new JPA account manager.
	 * 
	 * @param entityManager
	 *            the JPA entity manager
	 */
	public JpaRestaurantRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
		logger.info("Created JpaRestaurantRepository");
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public String getInfo() {
		return INFO;
	}

	public Restaurant findByMerchantNumber(String merchantNumber) {
		return (Restaurant) entityManager.createQuery("from Restaurant r where r.number = :merchantNumber")
				.setParameter("merchantNumber", merchantNumber).getSingleResult();
	}

	@Override
	public Long getRestaurantCount() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(Restaurant.class)));

		return entityManager.createQuery(cq).getSingleResult();
	}
}
