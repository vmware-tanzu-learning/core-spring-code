package rewards;

import config.RewardsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

@Configuration
@Import(RewardsConfig.class)
public class SystemTestConfig {
	
	// TODO-04 : Spring Boot will create the DataSource for us
	//           Comment out @Bean so this method is no longer called

	// TODO-08 : Switch back to imperative `DataSource` configuration
    //           Restore the @Bean method

	/**
	 * Creates an in-memory "rewards" database populated 
	 * with test data for fast testing
	 */
//	@Bean
	public DataSource dataSource(){
		return
			(new EmbeddedDatabaseBuilder())
			.addScript("classpath:rewards/testdb/schema.sql")
			.addScript("classpath:rewards/testdb/data.sql")
			.build();
	}	
	
}
