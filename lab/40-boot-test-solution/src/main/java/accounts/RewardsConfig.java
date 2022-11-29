package accounts;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import config.AppConfig;

@Configuration
@Import(AppConfig.class)
@EntityScan("rewards.internal")
public class RewardsConfig {

}