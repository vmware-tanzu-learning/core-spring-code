package rewards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

// TODO-01: Review dependency as described in the TO-DO-01 
//          in the pom.xml or build.gradle 
//
// TODO-07: Configure JPA as specified in the TO-DO-07 in the
//          src/test/resources/application.properties

@SpringBootApplication
@ComponentScan("config")
public class RewardsApplication {
	private final Logger logger = LoggerFactory.getLogger(RewardsApplication.class);

	private static final String SQL = "SELECT count(*) FROM T_ACCOUNT";

	public static void main(String[] args) {
		SpringApplication.run(RewardsApplication.class, args);
	}

	@Component
	public final class QueryAccountCountRunner implements CommandLineRunner {

		@Autowired
		private JdbcTemplate jdbcTemplate;

		@Override
		public void run(String... args) throws Exception {
			long accountCount = this.jdbcTemplate.queryForObject(SQL, Long.class);
			logger.info("Number of accounts:{}", accountCount);
		}
	}
}
