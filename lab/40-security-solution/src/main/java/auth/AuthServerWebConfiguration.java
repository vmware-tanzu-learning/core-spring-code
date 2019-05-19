package auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Setup a mini web-console for our server. Uses server-side rendering of
 * Mustache templates to generate its web-pages.
 */
@Configuration
public class AuthServerWebConfiguration implements WebMvcConfigurer {

	/**
	 * View controllers allow static pages to be rendered using templates for a
	 * consistent look and feel. No @Controller classes are required.
	 * <p>
	 * This setup defines a home page so we can display an "admin" console (and know
	 * the application is running). Also defines some additional pages to support
	 * login and logout from a browser.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("dashboard");

		// Maps URLs to a "template" (or view) of the same name - see
		// /src/main/resources/templates.
		registry.addViewController("/dashboard");
		registry.addViewController("/login");
		registry.addViewController("/denied");
		registry.addViewController("/done");
		registry.addViewController("/superuser-only");
	}

}
