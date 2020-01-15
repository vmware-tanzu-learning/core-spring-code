package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Configure URL access, user login and logout.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.exceptionHandling()
				.accessDeniedPage("/denied")
				.and()
			.authorizeRequests()
				.mvcMatchers("/accounts/resources/**").permitAll()
				.mvcMatchers("/accounts/edit*").hasRole("EDITOR")
				.mvcMatchers("/accounts/account*").hasAnyRole("VIEWER", "EDITOR")
				.mvcMatchers("/accounts/**").authenticated()
				.and()
			.logout()
				.permitAll()
				.logoutSuccessUrl("/");
	}

	/**
	 * Spring automatically calls this method (because it is autowired) to setup
	 * global security definitions. Note that SHA-256 encryption is enabled.
	 *
	 * To understand why this is an Autowired method, refer to the Security slides
	 * in the Student Handout PDF. Look for "Advanced Security - Global
	 * Configuration Choices".
	 * 
	 * @param auth
	 *            The authentication manager builder.
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		auth //
			.inMemoryAuthentication() //
				.withUser("viewer") //
					.password(passwordEncoder.encode("viewer")) //
					.roles("VIEWER") //
				.and() //
				.withUser("editor") //
			        .password(passwordEncoder.encode("editor")) //
					.roles("EDITOR");
	}
	
}
