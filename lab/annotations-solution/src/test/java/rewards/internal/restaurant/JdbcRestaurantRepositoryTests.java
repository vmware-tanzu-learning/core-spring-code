package rewards.internal.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import common.money.Percentage;

/**
 * Tests the JDBC restaurant repository with a test data source to verify data access and relational-to-object mapping
 * behavior works as expected.
 */
public class JdbcRestaurantRepositoryTests {

	private JdbcRestaurantRepository repository;

	@BeforeEach
	public void setUp() throws Exception {
		// simulate the Spring bean initialization lifecycle:

		// first, construct the bean
		repository = new JdbcRestaurantRepository();

		// then, inject its dependencies
		repository.setDataSource(createTestDataSource());

		// lastly, initialize the bean
		repository.populateRestaurantCache();
	}

	@AfterEach
	public void tearDown() throws Exception {
		// simulate the Spring bean destruction lifecycle:

		// destroy the bean
		repository.clearRestaurantCache();
	}

	@Test
	public void findRestaurantByMerchantNumber() {
		Restaurant restaurant = repository.findByMerchantNumber("1234567890");
		assertNotNull(restaurant, "restaurant is null - repository cache not likely initialized");
		assertEquals("1234567890", restaurant.getNumber(), "number is wrong");
		assertEquals("AppleBees", restaurant.getName(), "name is wrong");
		assertEquals(Percentage.valueOf("8%"), restaurant.getBenefitPercentage(), "benefitPercentage is wrong");
	}

	@Test
	public void findRestaurantByBogusMerchantNumber() {
		try {
			repository.findByMerchantNumber("bogus");
			fail("Should have thrown EmptyResultDataAccessException for a 'bogus' merchant number");
		} catch (EmptyResultDataAccessException e) {
			// expected
		}
	}

	@Test
	public void restaurantCacheClearedAfterDestroy() throws Exception {
		// force early tear down
		tearDown();
		try {
			// try what normally is a valid number
			repository.findByMerchantNumber("1234567890");
			fail("Should have thrown EmptyResultDataAccessException - cache not cleared?");
		} catch (EmptyResultDataAccessException e) {
			// expected
		}
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder()
			.setName("rewards")
			.addScript("/rewards/testdb/schema.sql")
			.addScript("/rewards/testdb/data.sql")
			.build();
	}
}
