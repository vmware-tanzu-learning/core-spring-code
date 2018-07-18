package rewards;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import config.RewardsConfig;

@Configuration
// TODO-04: Modify this Configuration class to import XML file resources instead
// of Java Config. The two XML files to import are "rewards-config.xml" and
// "test-infrastructure-config.xml" You will need to specify the classpath
// locations of the files.
// Remove or comment-out the @Import and dataSource bean method; everything
// will now be defined via XML.
// Save your work and re-run the last test, it should pass.
@Import(RewardsConfig.class)
public class TestInfrastructureConfig {

	/**
	 * Creates an in-memory "rewards" database populated with test data for fast
	 * testing
	 */
	@Bean
	public DataSource dataSource() {
		return (new EmbeddedDatabaseBuilder())
				.addScript("classpath:rewards/testdb/schema.sql")
				.addScript("classpath:rewards/testdb/data.sql").build();
	}
}
