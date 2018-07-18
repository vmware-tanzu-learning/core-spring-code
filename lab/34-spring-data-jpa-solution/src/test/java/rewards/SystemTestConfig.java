package rewards;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import config.RewardsConfig;


@Configuration
@EntityScan("rewards")
@EnableAutoConfiguration
@Import(RewardsConfig.class)
public class SystemTestConfig {
		
}
