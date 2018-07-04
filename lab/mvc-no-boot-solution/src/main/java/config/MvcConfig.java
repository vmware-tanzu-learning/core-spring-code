package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("accounts.web")
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * Establish a ViewResolver that adds a prefix / suffix to 
	 * the logical view name and selects the JstlView for rendering.
	 */
	@Bean
	public ViewResolver irvr(){
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setPrefix("/WEB-INF/views/");
		vr.setSuffix(".jsp");
		return vr;
	}
	
	/**
	*	Map URL /resources/* to serve static resources from classpath:/static/*
	*	This allows us to store and distribute css, images, etc. in JAR file.
	*	This is the equivalent of <mvc:resources/>
	*/
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	   registry.addResourceHandler("/resources/**")
	           .addResourceLocations("classpath:/static/");
	 }    	

}
