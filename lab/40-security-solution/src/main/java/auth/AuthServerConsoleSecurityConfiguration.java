package auth;

import org.springframework.context.annotation.Configuration;
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
@Configuration
public class AuthServerConsoleSecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * Define a master user. This has nothing to do with the OAuth2 configuration.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
	}

	/**
	 * Restrict the home page to the master user. In a real system you might provide
	 * an administration dashboard like this to control/configure the server.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin() //
				.loginPage("/login").permitAll() // Enable a login page
			.and() //
				.exceptionHandling().accessDeniedPage("/denied") // Access-denied page (unused)
			.and().authorizeRequests() //
				.mvcMatchers("/resources/**", "/oauth/**").permitAll() //
				.mvcMatchers("/illegal").hasRole("SUPERUSER")
				.mvcMatchers("/**").hasRole("ADMIN") //
			.and() //
				.logout().permitAll().logoutSuccessUrl("/done");
	}
}
