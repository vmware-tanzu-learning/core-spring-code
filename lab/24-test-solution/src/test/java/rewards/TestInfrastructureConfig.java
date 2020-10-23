package rewards;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import config.RewardsConfig;

@Configuration
@Import({
	TestInfrastructureLocalConfig.class,
	TestInfrastructureJndiConfig.class,
	RewardsConfig.class })
public class TestInfrastructureConfig {

	/**
	 * The bean logging post-processor from the bean lifecycle slides.
	 */
	@Bean
	public static LoggingBeanPostProcessor loggingBean(){
		return new LoggingBeanPostProcessor();
	}
}
