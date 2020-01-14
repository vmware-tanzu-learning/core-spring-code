package auth;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// TODO-04: Enable this configuration by removing // comment characters
//@EnableWebSecurity
public class AuthServerConsoleSecurityConfiguration extends WebSecurityConfigurerAdapter {

	public static final String ADMIN_USER = "admin";
	public static final String ADMIN_PASSWORD = "admin";
	public static final String ADMIN_ROLE = "ADMIN";

	public static final String SUPERUSER_USER = "superuser";
	public static final String SUPERUSER_PASSWORD = "superuser";
	public static final String SUPERUSER_ROLE = "SUPERUSER";

	/**
	 * Add users. 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		PasswordEncoder passwordEncoder
				= PasswordEncoderFactories.createDelegatingPasswordEncoder();

		// TODO-05: Add two users - ADMIN_USER and SUPERUSER_USER with encoded passwords
		auth.inMemoryAuthentication()
		// Add ADMIN_USER/ADMIN_PASSWORD with ADMIN_ROLE

		.and()
		// Add SUPERUSER_USER/SUPERUSER_PASSWORD with ADMIN_ROLE and SUPERUSER_ROLE

		;
	}

	/**
	 * Configure access control. In a real system you might provide
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
				// TODO-06a: Allow "/superuser*" pages accessible only by SUPERUSER_ROLE

				// TODO-06b: Allow all remaining pages accessible by ADMIN_ROLE

			.and() //
				// Enable logout and redirect to /done afterwards
				.logout().permitAll().logoutSuccessUrl("/done");
		
		// TODO-07: Let's see if it works ...
		// - Save your changes and wait for the application to restart.
		//   (If you are using IntelliJ, you will have to trigger recompilation of this class.)
		// - Go to http://localhost:1111 again.
		// - Log in with "admin"/"admin".  You should be able to access pages
		//   except Superuser only page. Log out.
		// - Log in with "superuser"/"superuser": You should be able to access all pages
		//   including Superuser only page
		
	}
}
