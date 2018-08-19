package servers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Setup a mini web-console for our server.
 */
@Configuration
public class AuthServerWebConfiguration implements WebMvcConfigurer {

	/**
	 * Provide a home page so we can see it's running (the console) and some
	 * additional pages to support login and logout from a browser.
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("console");

		// These are used once browser-based login is enabled
		registry.addViewController("/console");
		registry.addViewController("/login");
		registry.addViewController("/denied");
		registry.addViewController("/done");
		registry.addViewController("/illegal");
	}

}
