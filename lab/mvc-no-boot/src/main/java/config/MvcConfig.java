package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * TODO-02: Add an InternalResourceViewResolver bean definition.
 * <p>
 * Set the prefix and suffix properties - the JSP views are all in
 * <code>src/main/webapp/WEB-INF/views</code>. Refer to the notes for help.
 */

@Configuration
@ComponentScan("accounts.web")
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * Map URL /resources/* to serve static resources from classpath:/static/*
	 * This allows us to store and distribute css, images, etc. in JAR file.
	 * This is the equivalent of <mvc:resources/>
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/");
	}
}
