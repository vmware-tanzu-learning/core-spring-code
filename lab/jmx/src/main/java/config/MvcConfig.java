/**
 * 
 */
package config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import rewards.RewardNetwork;
import rewards.web.RewardsController;

/**
 * Configure the web-related beans
 */
public class MvcConfig implements WebMvcConfigurer {

	/**
	 * Create the {@link RewardsController}.
	 * 
	 * @param rewardNetwork
	 * @return
	 */
	@Bean
	public RewardsController rewardsController(RewardNetwork rewardNetwork) {
		return new RewardsController(rewardNetwork);
	}

	/**
	 * A the view-controller registry is a convenient way of mapping a URL to a view
	 * without needing to write a controller. By default the URL "/xxx" maps to a
	 * logical view name called "xxx" unless a view-name is explicitly set.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/rewardConfirmation");
		registry.addViewController("/rewardError");
	}
}
