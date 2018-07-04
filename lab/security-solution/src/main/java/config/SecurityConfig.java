package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
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
	 * <p>
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
		auth //
			.inMemoryAuthentication() //
				.passwordEncoder(new StandardPasswordEncoder())
				.withUser("vince") //
					.password("08c461ad70fce6c74e12745931085508ccb2090f2eae3707f6b62089c634ddd2636f380f40109dfb") //
					.roles("VIEWER") //
				.and() //
				.withUser("edith") //
					.password("4cfbf05e4493d17125c547fdba494033d7aceee9310f253f3e96c4f928333d2436d669d63a84fe4f") //
					.roles("EDITOR");
	}
	
}
