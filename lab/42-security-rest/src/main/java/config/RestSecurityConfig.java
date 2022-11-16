package config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// TODO-10: Enable method security
// - Add @EnableMethodSecurity annotation to this class

@Configuration
public class RestSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// @formatter:off
        http.authorizeHttpRequests((authz) -> authz
                // TODO-04: Configure authorization using requestMatchers method
                // - Allow DELETE on the /accounts resource (or any sub-resource)
                //   for "SUPERADMIN" role only
                // - Allow POST or PUT on the /accounts resource (or any sub-resource)
                //   for "ADMIN" or "SUPERADMIN" role only
                // - Allow GET on the /accounts resource (or any sub-resource)
                //   for all roles - "USER", "ADMIN", "SUPERADMIN"
        		// - Allow GET on the /authorities resource
                //   for all roles - "USER", "ADMIN", "SUPERADMIN"

                // Deny any request that doesn't match any authorization rule
                .anyRequest().denyAll())
        .httpBasic(withDefaults())
        .csrf(CsrfConfigurer::disable);
        // @formatter:on

        return http.build();
	}

	// TODO-14b (Optional): Remove the InMemoryUserDetailsManager definition
	// - Comment the @Bean annotation below
	
	@Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {

		// TODO-05: Add three users with corresponding roles:
		// - "user"/"user" with "USER" role (example code is provided below)
		// - "admin"/"admin" with "USER" and "ADMIN" roles
		// - "superadmin"/"superadmin" with "USER", "ADMIN", and "SUPERADMIN" roles
		// (Make sure to store the password in encoded form.)
    	// - pass all users in the InMemoryUserDetailsManager constructor
		UserDetails user = User.withUsername("user").password(passwordEncoder.encode("user")).roles("USER").build();

		return new InMemoryUserDetailsManager(user /* Add new users comma-separated here */);
	}
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
