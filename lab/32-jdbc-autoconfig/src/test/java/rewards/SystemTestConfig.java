package rewards;

import config.RewardsConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

/**
 * Sets up an embedded in-memory HSQL database, primarily for testing.
 */
@Configuration
@Import(RewardsConfig.class)
public class SystemTestConfig {
	private final Logger logger = LoggerFactory.getLogger(SystemTestConfig.class);

	// TODO-04 : Spring Boot will create the DataSource for us
	//           Comment out @Bean so this method is no longer called

	// TODO-10 : Switch back to explicit `DataSource` configuration
    //           Restore the @Bean method
	//           The scripts have moved to the root of the classpath,
	//           so you need to change the addScript() calls.
	//           NOTE the debug logging

	/**
	 * Creates an in-memory "rewards" database populated 
	 * with test data for fast testing
	 */
	@Bean
	public DataSource dataSource() {
		logger.debug("Creating the datasource bean explicitly");

		return
			(new EmbeddedDatabaseBuilder())
			.addScript("classpath:rewards/testdb/schema.sql")
			.addScript("classpath:rewards/testdb/data.sql")
			.build();
	}	
	
}
