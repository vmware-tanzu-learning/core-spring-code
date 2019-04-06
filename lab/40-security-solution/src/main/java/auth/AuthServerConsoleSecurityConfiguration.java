package auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configure browser-based access. Restrict access to the admin pages to the
 * admin user and provide for login and logout.
 * <p>
 * <b>NOTE:</b> This has nothing to do with the ability to use this process as
 * an OAuth2 Authorization server. That configuration is in the
 * {@link AuthorizationServer}.
 */
@Configuration
@EnableWebSecurity
public class AuthServerConsoleSecurityConfiguration extends WebSecurityConfigurerAdapter {

	public static final String ADMIN_USER = "admin";
	public static final String ADMIN_PASSWORD = "admin";
	public static final String ADMIN_ROLE = "ADMIN";

	/**
	 * Define a master user. This has nothing to do with the OAuth2 configuration.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
			.withUser(ADMIN_USER).password(passwordEncoder.encode(ADMIN_PASSWORD)).roles(ADMIN_ROLE);
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
