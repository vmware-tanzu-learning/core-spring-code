package rewards;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import config.RewardsConfig;

@Configuration
@Import({
	TestInfrastructureDevConfig.class,
	TestInfrastructureProductionConfig.class,
	RewardsConfig.class })
public class TestInfrastructureConfig {

	public LoggingBeanPostProcessor loggingBean(){
		return new LoggingBeanPostProcessor();
	}
}
