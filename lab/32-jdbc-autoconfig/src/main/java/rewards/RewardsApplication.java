package rewards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO-03 : Turn the RewardsApplication into a Spring Boot application

public class RewardsApplication {
    private final Logger logger
            = LoggerFactory.getLogger(RewardsApplication.class);
    
    private static final String SQL = "SELECT count(*) FROM T_ACCOUNT";

    // TODO-05 : Move the resources from the
    // `src/test/resources/rewards.testdb` package to the
    //  `src/main/resources/` package

    // TODO-06 : Setup command line runner that will query count from
    //  T_ACCOUNT table and print the count to the console
}
