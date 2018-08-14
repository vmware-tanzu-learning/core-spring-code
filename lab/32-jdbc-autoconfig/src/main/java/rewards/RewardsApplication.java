package rewards;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

// TODO-01 : Open pom.xml, look for TO-DO-01

// TODO-02 : In pom.xml  look for TO-DO-02

// TODO-03 : Turn the RewardsApplication into a Spring Boot application
//           by adding an annotation and a main method.

@SpringBootApplication
public class RewardsApplication {
    private final Logger logger
            = LoggerFactory.getLogger(RewardsApplication.class);
    
    private static final String SQL = "SELECT count(*) FROM T_ACCOUNT";

    public static void main(String[] args) {
		SpringApplication.run(RewardsApplication.class, args);
	}

    // TODO-05 : Move the SQL scripts from the
    //             `src/test/resources/rewards/testdb` to the
    //             `src/main/resources/`
    //  Why are you doing this?  See detailed instructions if you do not know.

    // TODO-06 : Setup a command line runner that will query count from
    //           T_ACCOUNT table and log the count to the console
    //           Use the SQL query and logger provided above.
    //           Use the JdbcTemplate bean that Spring Boot creates for you
    //           automatically.
    
    /**
     * Spring Boot automatically invokes the run() method of any
     * CommandLineRunner Spring Bean it finds.
     */
    @Component
    public class JdbcApplicationRunner implements CommandLineRunner {
        @Autowired
        JdbcTemplate jdbcTemplate;

        @Override
        public void run(String... args) throws Exception {
            // Do some work ...
            logger.info("Hello, there are "
                    + jdbcTemplate.queryForObject(SQL, Long.class)
                    + " accounts");
        }
    }
}
