package rewards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class JdbcBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcBootApplication.class, args);
	}

	@Component
	public class JdbcApplicationRunner implements CommandLineRunner {
		@Autowired
		JdbcTemplate jdbcTemplate;

		public static final String QUERY = "SELECT count(*) FROM T_ACCOUNT";

		@Override
		public void run(String... args) throws Exception {

			System.out.println("Hello, there are "
					+ jdbcTemplate.queryForObject(QUERY, Long.class)
					+ " accounts");
		}
	}
}