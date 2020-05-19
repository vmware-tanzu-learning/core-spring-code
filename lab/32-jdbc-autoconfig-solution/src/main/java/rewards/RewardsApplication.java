package rewards;

import config.RewardsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@EnableConfigurationProperties(RewardsRecipientProperties.class)
//@ConfigurationPropertiesScan
@Import(RewardsConfig.class)
public class RewardsApplication {
    private final Logger logger
            = LoggerFactory.getLogger(RewardsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RewardsApplication.class,args);
    }

    @Bean
    CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate){

        String QUERY = "SELECT count(*) FROM T_ACCOUNT";

        Long numberOfAccounts = jdbcTemplate.queryForObject(QUERY, Long.class);

        return args -> logger.info("Hello, there are {} accounts", numberOfAccounts);
    }

    @Bean
    CommandLineRunner commandLineRunner2(RewardsRecipientProperties rewardsRecipientProperties) {
        return args -> logger.info("Recipient: " + rewardsRecipientProperties.getName());
    }
}
