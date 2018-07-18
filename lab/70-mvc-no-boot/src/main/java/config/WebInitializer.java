package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Java-based web configuration file, replacement for web.xml
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * One way to select an active profile, set a system property before
	 * startup:
	 */
	public WebInitializer() {
		super();
		System.setProperty("spring.profiles.active", "jpa");
	}

	/**
	 * Tell Spring what configuration class(es) to use for the Root context:
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	/**
	 * Tell Spring what configuration class(es) to use for the DispatcherServlet
	 * context:
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { MvcConfig.class };
	}

	/**
	 * Tell Spring what mapping to use for the DispatcherServlet:
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/accounts/*" };
	}

	// TODO-01: Examine the web configuration entries above.
	// Deploy this web application to the server, right click on the
	// project and do "Run As -> Run on Server"
	// If no server is defined in STS (check Servers tab) you will need to
	// create one - see the Appendix in the lab-instructions for how
	//
	// Once deployed:
	// 1. Navigate to http://localhost:8080/mvc
	// 2. Click the View Account List, you should see a list of accounts.
	// 3. Click any of the account links, you will get a 404.
	// We will implement the missing details page as part of the lab.
	//
	// >> TROUBLESHOOTING
	// If your application deploys successfully there should be a lot of logging
	// in the console window.
	// If you have problems, before asking for help, try the following
	// 1. Right-click on the server in the Servers panel and select Clean ...
	// 2. Right-click (again) on the server and select Clean Work Directory ...
	// 3. Now try running again

}
