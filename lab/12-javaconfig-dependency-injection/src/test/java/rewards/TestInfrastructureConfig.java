package rewards;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

/**
 * TODO-06: Review this class - it creates an in-memory HSQL database and
 * populates it with data. If you are interested, the two scripts are in the
 * 'rewards-common' project in 'src/main/resources'.
 *
 * DO NOT MODIFY THE BEAN METHOD.
 *
 * TODO-07: Import your application configuration file (RewardsConfig)
 *
 * TODO-08: Create a new JUnit 5 test called RewardNetworkTests
 * in this package (your IDE may call it JUnit Jupiter). Ask for
 * a setup() method to be generated.
 *
 * NOTE: The appendices at the bottom of the course Home Page includes
 * a section on creating JUnit tests in an IDE.
 *
 * TODO-09: There should be a setup() method already, if not add one and
 * annotate it with @BeforeEach.
 * In this method, you will need to create an application context using
 * this configuration class, then get the rewardNetwork bean and assign
 * it to a private field for use later.
 *
 * TODO-10: We can test the setup by running an empty test.
 *
 *  - If your IDE automatically generated a @Test method, rename it
 *    testRewardForDining. Delete any code in the method body.
 *  - Otherwise add a testRewardForDining method & annotate it with
 *    @Test (make sure to import org.junit.jupiter.api.Test).
 *  - Run the test. If your setup() is working you get a green bar.
 *
 * TODO-11: Finally run a real test.
 *
 *  - Copy the unit test (the @Test method) from
 *    RewardNetworkImplTests#testRewardForDining() - we are testing
 *    the same code, but using a different setup.
 *  - Run the test - it should pass if you have configured everything
 *    correctly. Congratulations, you are done.
 *  - If your test fails - did you miss the import in TO DO 7 above?
 *
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
