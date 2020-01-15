package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * Spring does not automatically add the currently logged in user (the
 * Principal) to the Model. This interceptor will do so.
 */
class ParameterInterceptor implements HandlerInterceptor {
	/**
	 * The post-handle method is invoked for EVERY request, after the request
	 * handler (@Controller method) has been invoked and before the View is
	 * processed to create the response.
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Principal principal = request.getUserPrincipal();

		if (principal != null)
			modelAndView.addObject("principal", principal);
	}
}

@Configuration
@ComponentScan("accounts.web")
public class MvcConfig implements WebMvcConfigurer {

	/**
	 * These views are so simple they do not need a controller:
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/index");
		registry.addViewController("/login");
		registry.addViewController("/denied");
		registry.addViewController("/hidden");
		registry.addViewController("/accounts/hidden").setViewName("hidden");
	}

	/**
	 * Add a custom HandlerInterceptor to ensure the Principal is always added to
	 * every request (if ther is one).
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ParameterInterceptor());
	}

}
