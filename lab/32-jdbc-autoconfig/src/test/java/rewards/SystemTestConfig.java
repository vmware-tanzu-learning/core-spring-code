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

	// TODO-08 : Use Spring Boot auto-configuration for DataSource
	// - Note that this test configuration class is used by
	//   RewardNetworkTests in the same package
	// - Comment out the @Bean method below so this method
	//   is no longer called

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
