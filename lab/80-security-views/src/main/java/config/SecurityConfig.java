package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//	TODO-04: Set the login page (below) so it uses '/login' and the access denied page so it uses '/denied'.
		//  - Click on the "View Account List" - http://localhost:8080/accounts/accountList
		//	  You should now be taken to the login page
		//	- Try to log in using an incorrect user/password such as 'foo', 'foo'.
		//    Note that the you are sent back to the login page with a friendly message.
		//	- Take a look at login.html to see if you understand how the page
		//	  displays the error message in response to a bad username or password.
		
		//	TODO-05: Check the username/password of the user defined in configureGlobal (below)
		//	and use it to log into the application.
		//	However this user is NOT allowed to access the 'Account List' page yet. 
		//	After log in, you SHOULD expect to see the access denied page - set this to
		//  be '/denied' below. 

		http
			.formLogin()
				.loginPage("/TODO-04a")
				.permitAll()  // Unrestricted access to the login page
				.and()
			.exceptionHandling()
				.accessDeniedPage("/TODO-04b")
				.and()
				
		//	TODO-06: As defined below, users with role EDITOR can already access '/accounts/account*'. 
		//	- Update the configuration so users with role VIEWER can ALSO access that same URL pattern.
		//	- After completing this task, let the web application redeploy.
		//  - Login as user 'viewer' and you should now be able to access the
		//	  account list and account details.
		//	- Try to Edit the Account Details again verify you get an Access Denied error because
		//	  'viewer' is not an EDITOR.
				
			.authorizeRequests()
				.mvcMatchers("/accounts/resources/**").permitAll()
				.mvcMatchers("/accounts/edit*").hasRole("EDITOR")
				.mvcMatchers("/accounts/account*").hasRole("EDITOR")
				.and()
				
		//	TODO-07: Log out by clicking on the 'log out' link. 
		//	- Then try to access 'http://localhost:8080/accounts/hidden'.
		//	  As you can see, this URL is currently not protected.
		//	- Add an mvcMatchers with pattern /accounts/** to serve as a catch-all BELOW all other mvcMatchers calls.
		//	  For this pattern, all users should be authenticated (no specific role required).
		//	- Save and try to access this URL again, you should now be redirected to the login page.
				
			.logout()
				.permitAll()
				.logoutSuccessUrl("/");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		//	TODO-08: Add a user editor/editor with role 'EDITOR' (hint, use the and() method).
		//	 - Save and let the web application restart.
		//	 - If you don't see the login form, log out from the application by clicking on the "log out" link.
		//	 - You can now log in with the user 'editor'.
		//	 - Once logged in, click one of the links to reach the  account details page,
		//	   then click the "Edit account" link.
		//	 - You will be able to edit account details. Try to log in again using
		//	   'viewer' and double-check that viewer, who only has 'VIEWER' rights,
		//	   is still not allowed to edit account information.

		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		auth
			.inMemoryAuthentication()
				.withUser("viewer").password(passwordEncoder.encode("viewer")).roles("VIEWER");
	}
	
}
