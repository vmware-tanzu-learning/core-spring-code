package rewards;

import config.RewardsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Sets up an embedded in-memory HSQL database, primarily for testing.
 */
@Configuration
@Import(RewardsConfig.class)
public class SystemTestConfig {
	private final Logger logger = LoggerFactory.getLogger(SystemTestConfig.class);

	/**
	 * Creates an in-memory "rewards" database populated 
	 * with test data for fast testing
	 */
//	@Bean
//	public DataSource dataSource() {
//		logger.debug("Creating the datasource bean explicitly");
//
//		return
//			(new EmbeddedDatabaseBuilder())
//			.addScript("classpath:rewards/testdb/schema.sql")
//			.addScript("classpath:rewards/testdb/data.sql")
//			.build();
//	}
	
}
