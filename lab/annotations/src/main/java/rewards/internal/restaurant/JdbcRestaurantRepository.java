package rewards.internal.restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import common.money.Percentage;

/**
 * Loads restaurants from a data source using the JDBC API.
 *
 * This implementation should cache restaurants to improve performance. The
 * cache should be populated on initialization and cleared on destruction.
 */

/*
 * TODO-06: Annotate the class with an appropriate stereotype annotation to
 * cause component-scan to detect and load this bean. Configure Dependency
 * Injection for dataSource. Use constructor injection in this case (note the
 * logic in the constructor requires a dataSource).
 */

/*
 * TODO-08: Change the configuration to set the dataSource property using
 * setDataSource(). To do this you must MOVE the @Autowired annotation. Neither
 * constructor should be annotated with @Autowired now, so Spring uses the
 * default constructor by default.
 *
 * Re-run the test. It should fail. Examine the stack trace and see if you can
 * understand why. (If not, refer to the detailed lab instructions). We will fix
 * this error in the next step."
 */

public class JdbcRestaurantRepository implements RestaurantRepository {

	private DataSource dataSource;

	/**
	 * The Restaurant object cache. Cached restaurants are indexed by their merchant
	 * numbers.
	 */
	private Map<String, Restaurant> restaurantCache;

	/**
	 * The constructor sets the data source this repository will use to load
	 * restaurants. When the instance of JdbcRestaurantRepository is created, a
	 * Restaurant cache is populated for read only access
	 *
	 * @param dataSource
	 *            the data source
	 */

	public JdbcRestaurantRepository(DataSource dataSource) {
		this.dataSource = dataSource;
		this.populateRestaurantCache();
	}

	public JdbcRestaurantRepository() {
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Restaurant findByMerchantNumber(String merchantNumber) {
		return queryRestaurantCache(merchantNumber);
	}

	/**
	 * Helper method that populates the {@link #restaurantCache restaurant object
	 * cache} from rows in the T_RESTAURANT table. Cached restaurants are indexed by
	 * their merchant numbers. This method should be called on initialization.
	 */

	/*
	 * TODO-09: Mark this method with an annotation that will cause it to be
	 * executed by Spring after constructor & setter initialization has occurred.
	 *
	 * Re-run the RewardNetworkTests test. You should see the test succeed.
	 *
	 * Populating the cache is not really a valid construction activity, so using a
	 * post-construct, rather than the constructor, is better practice.
	 */

	void populateRestaurantCache() {
		restaurantCache = new HashMap<String, Restaurant>();
		String sql = "select MERCHANT_NUMBER, NAME, BENEFIT_PERCENTAGE from T_RESTAURANT";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Restaurant restaurant = mapRestaurant(rs);
				// index the restaurant by its merchant number
				restaurantCache.put(restaurant.getNumber(), restaurant);
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQL exception occurred finding by merchant number", e);
		} finally {
			if (rs != null) {
				try {
					// Close to prevent database cursor exhaustion
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (ps != null) {
				try {
					// Close to prevent database cursor exhaustion
					ps.close();
				} catch (SQLException ex) {
				}
			}
			if (conn != null) {
				try {
					// Close to prevent database connection exhaustion
					conn.close();
				} catch (SQLException ex) {
				}
			}
		}
	}

	/**
	 * Helper method that simply queries the cache of restaurants.
	 *
	 * @param merchantNumber
	 *            the restaurant's merchant number
	 * @return the restaurant
	 * @throws EmptyResultDataAccessException
	 *             if no restaurant was found with that merchant number
	 */
	private Restaurant queryRestaurantCache(String merchantNumber) {
		Restaurant restaurant = restaurantCache.get(merchantNumber);
		if (restaurant == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return restaurant;
	}

	/**
	 * Helper method that clears the cache of restaurants. This method should be
	 * called on destruction.
	 * <p>
	 * TODO-10: To see if this method is being invoked either 1) add a breakpoint
	 * and use the debugger or 2) use <code>System.out.println</code> to write a
	 * message to the console.
	 * <p>
	 * TODO-11: Re-run RewardNetworkTests. You should see that this method is never
	 * called. Use an annotation to register this method for a destruction lifecycle
	 * callback. Re-run the test and you should be able to see that this method is
	 * now being called.
	 */
	public void clearRestaurantCache() {
		restaurantCache.clear();
	}

	/**
	 * Maps a row returned from a query of T_RESTAURANT to a Restaurant object.
	 *
	 * @param rs
	 *            the result set with its cursor positioned at the current row
	 */
	private Restaurant mapRestaurant(ResultSet rs) throws SQLException {
		// get the row column data
		String name = rs.getString("NAME");
		String number = rs.getString("MERCHANT_NUMBER");
		Percentage benefitPercentage = Percentage.valueOf(rs.getString("BENEFIT_PERCENTAGE"));
		// map to the object
		Restaurant restaurant = new Restaurant(number, name);
		restaurant.setBenefitPercentage(benefitPercentage);
		return restaurant;
	}
}