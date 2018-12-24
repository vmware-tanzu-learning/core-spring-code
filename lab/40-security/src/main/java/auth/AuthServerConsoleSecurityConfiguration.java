package auth;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configure browser-based access. Restrict access to the admin pages to the
 * admin user and provide for login and logout.
 * <p>
 * <b>NOTE:</b> This has nothing to do with the ability to use this process as
 * an OAuth2 Authorization server. That configuration is in the
 * {@link AuthorizationServer}.
 */
// TODO-04: Enable this configuration by removing both the // comment characters
//@Configuration
//@EnableWebSecurity
public class AuthServerConsoleSecurityConfiguration extends WebSecurityConfigurerAdapter {

	public static final String ADMIN_USER = "admin";
	public static final String ADMIN_PASSWORD = "admin";
	public static final String ADMIN_ROLE = "ADMIN";

	/**
	 * Define a master user. This has nothing to do with the OAuth2 configuration.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO-05: Add an in-memory user-details service configurer and
		// setup a single user using the ADMIN_??? constants above.
	}

	/**
	 * Restrict the home page to the master user. In a real system you might provide
	 * an administration dashboard like this to control/configure the server.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin() //
		         // Setup a login page, and allow open-access
				.loginPage("/login").permitAll() // Enable a login page
			.and() //
			    // Define a custom Access Denied page
				.exceptionHandling().accessDeniedPage("/denied") // Access-denied page (unused)
			.and().authorizeRequests() //
			     // Allow open-access to resources (images, CSS, JavaScript)
				.mvcMatchers("/resources/**").permitAll() //
				// Hide this page from all except super-users 
				.mvcMatchers("/illegal").hasRole("SUPERUSER") //
				// TODO-06: Require all remaining URLs to need ADMIN role to access them
			.and() //
				// Enable logout and redirect to /done afterwards
				.logout().permitAll().logoutSuccessUrl("/done");
		
		// TODO-07: Let's see if it works ...
		// - Save your changes and wait for the application to restart.
		// - Go to http://locahost:1111 again. You should be forced to login
		//   as the "admin" user to see the console page.
		// - What happens if you try and access the Superuser Only page now?
		
	}
}
