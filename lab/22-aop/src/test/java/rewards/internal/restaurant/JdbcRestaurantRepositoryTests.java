package rewards.internal.restaurant;

import common.money.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the JDBC restaurant repository with a test data source to verify data access and relational-to-object mapping
 * behavior works as expected.
 */
public class JdbcRestaurantRepositoryTests {

	private JdbcRestaurantRepository repository;

	@BeforeEach
	public void setUp() throws Exception {
		repository = new JdbcRestaurantRepository();
		repository.setDataSource(createTestDataSource());
	}

	@Test
	public void testFindRestaurantByMerchantNumber() {
		Restaurant restaurant = repository.findByMerchantNumber("1234567890");
		assertNotNull(restaurant, "the restaurant should never be null");
		assertEquals("1234567890", restaurant.getNumber(), "the merchant number is wrong");
		assertEquals("AppleBees", restaurant.getName(), "the name is wrong");
		assertEquals(Percentage.valueOf("8%"), restaurant.getBenefitPercentage(), "the benefitPercentage is wrong");
		assertEquals(JdbcRestaurantRepository.AlwaysAvailable.INSTANCE,
				restaurant.getBenefitAvailabilityPolicy(), "the benefit availability policy is wrong");
	}

	@Test
	public void testFindRestaurantByBogusMerchantNumber() {
        assertThrows(EmptyResultDataAccessException.class, ()-> {
            repository.findByMerchantNumber("bogus");
        });
	}

	private DataSource createTestDataSource() {
		return new EmbeddedDatabaseBuilder()
			.setName("rewards")
			.addScript("/rewards/testdb/schema.sql")
			.addScript("/rewards/testdb/data.sql")
			.build();
	}
}
