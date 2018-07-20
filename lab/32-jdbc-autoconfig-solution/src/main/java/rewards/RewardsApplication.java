package rewards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan("config")
public class RewardsApplication {
    public static void main(String[] args) {
        SpringApplication.run(RewardsApplication.class,args);
    }

    @Autowired
    private DataSource dataSource;

    @Component
    public class JdbcApplicationRunner implements CommandLineRunner {

        JdbcTemplate jdbcTemplate;

        public JdbcApplicationRunner(DataSource dataSource) {
            this.jdbcTemplate = new JdbcTemplate(dataSource);
        }

        public static final String QUERY = "SELECT count(*) FROM T_ACCOUNT";

        @Override
        public void run(String... args) throws Exception {

            System.out.println("Hello, there are "
                    + jdbcTemplate.queryForObject(QUERY, Long.class)
                    + " accounts");
        }
    }
}
