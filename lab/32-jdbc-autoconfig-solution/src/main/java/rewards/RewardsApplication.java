package rewards;

import config.RewardsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Import(RewardsConfig.class)
public class RewardsApplication {
    private final Logger logger
            = LoggerFactory.getLogger(RewardsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RewardsApplication.class,args);
    }

    @Component
    public final class QueryAccountCountRunner
            implements CommandLineRunner {
        private static final String SQL = "SELECT count(*) FROM T_ACCOUNT";

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Override
        public void run(String... args) throws Exception {
            long accountCount
                    = this.jdbcTemplate.queryForObject(SQL, Long.class);
            logger.info("Number of accounts:{}", accountCount);
        }
    }
}
