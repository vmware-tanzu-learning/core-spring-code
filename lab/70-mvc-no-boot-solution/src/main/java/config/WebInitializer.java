package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Java-based web configuration file, replacement for web.xml
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer  {

	/**
	 * One way to select an active profile, set a system property before startup.
	 */
	public WebInitializer() {
		super();
		System.setProperty("spring.profiles.active", "jpa");
	}

	/**
	 * Tell Spring what configuration class(es) 
	 * to use for the Root context:
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{ RootConfig.class };
	}

	/**
	 * Tell Spring what configuration class(es) 
	 * to use for the DispatcherServlet context:
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{ MvcConfig.class };
	}

	/**
	 * Tell Spring what mapping to use 
	 * for the DispatcherServlet:
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/accounts/*"};
	}

}

