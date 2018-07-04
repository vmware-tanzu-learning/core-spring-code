package rewards;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import rewards.internal.RewardNetworkImplTests;

/**
 * TODO-06: Review this class - it creates an in-memory HSQL database and
 * populates it with data. If you are interested, the two scripts are in the
 * 'rewards-common' project in 'src/main/resources'.
 * <p>
 * DO NOT MODIFY THE BEAN METHOD.
 * <p>
 * TODO-07: Import your application configuration file (RewardsConfig)
 * <p>
 * TODO-08: Create a new JUnit Jupiter (JUnit 5) test called RewardNetworkTests
 * in this package.
 * <p>
 * TODO-09: Add a setup() method and annotate with @BeforeEach. In this method,
 * you will need to create an application context, get the rewardNetwork bean
 * and assign it to a private field for use in your test method.
 * <p>
 * TODO-10: Delete the test() method and copy the unit test (the @Test method)
 * from {@link RewardNetworkImplTests#testRewardForDining()} - we are testing
 * the same code, but using a different setup.
 * <p>
 * TODO-11: Run the test - it should pass if you have configured everything
 * correctly. Congratulations, you are done.
 */
@Configuration
public class TestInfrastructureConfig {

	/**
	 * Creates an in-memory "rewards" database populated with test data for fast
	 * testing
	 */
	@Bean
	public DataSource dataSource() {
		return (new EmbeddedDatabaseBuilder()) //
				.addScript("classpath:rewards/testdb/schema.sql") //
				.addScript("classpath:rewards/testdb/data.sql") //
				.build();
	}
}
