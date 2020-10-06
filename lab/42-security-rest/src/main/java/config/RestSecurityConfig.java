package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// TODO-10: Enable global method security
// - Add @EnableGlobalMethodSecurity(prePostEnabled = true)
//   annotation to this class

@Configuration
@EnableWebSecurity      // Redundant in Spring Boot app
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // TODO-04: Configure authorization using mvcMatchers method
                // - Allow DELETE on the /accounts resource (or any sub-resource)
                //   for "SUPERADMIN" role only
                // - Allow POST or PUT on the /accounts resource (or any sub-resource)
                //   for "ADMIN" or "SUPERADMIN" role only
                // - Allow GET on the /accounts resource (or any sub-resource)
                //   for all roles - "USER", "ADMIN", "SUPERADMIN"

                // For all other URL's, make sure the caller is authenticated
                .mvcMatchers("/**").authenticated()
                .and()
            .httpBasic()
                .and()
            .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // TODO-05: Add three users with corresponding roles:
        // - "user"/"user" with "USER" role (example code is provided below)
        // - "admin"/"admin" with "USER" and "ADMIN" roles
        // - "superadmin"/"superadmin" with "USER", "ADMIN", and "SUPERADMIN" roles
        // (Make sure to store the password in encoded form.)
        auth.inMemoryAuthentication()
            .withUser("user").password(passwordEncoder.encode("user")).roles("USER").and()

        ;

        // TODO-14 (Optional): Add authentication based upon the custom UserDetailsService
        // - Uncomment the line below and finish up the code
        //auth.

        // TODO-18 (Optional): Add authentication based upon the custom AuthenticationProvider
        // - Uncomment the line below and finish up the code
        //auth.
    }

}

// Optional exercise - Do the remaining steps only if you have extra time
// TODO-13 (Optional): Create custom UserDetailsService
// - Note that it needs to implement loadUserByUsername method
//   of the UserDetailsService interface
// - Uncomment the commented code fragment below so that this custom
//   UserDetailsService maintains UserDetails of two users:
//   - "mary"/"mary" with "USER" role and
//   - "joe"/"joe" with "USER" and "ADMIN" roles
class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User.UserBuilder builder = User.builder();
//        builder.username(username);
//        builder.password(passwordEncoder.encode(username));
//        switch (username) {
//            case "mary":
//                builder.roles("USER");
//                break;
//            case "joe":
//                builder.roles("USER", "ADMIN");
//                break;
//            default:
//                throw new UsernameNotFoundException("User not found.");
//        }

        return builder.build();
    }
}
// TODO-17 (Optional): Create custom AuthenticationProvider
// - Note that it needs to implement AuthenticationProvider interface
// - Uncomment the commented code fragment below so that this custom
//   AuthenticationProvider handles a user with the following credentials
//   - "spring"/"spring" with "ROLE_ADMIN" role
class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        if (checkCustomAuthenticationSystem(username, password)) {
//
//            return new UsernamePasswordAuthenticationToken(
//                    username, password, new ArrayList<>(Arrays.asList(new GrantedAuthority() {
//                @Override
//                public String getAuthority() {
//                    return "ROLE_ADMIN";
//                }
//            })));
//        } else {
//            return null;
//        }
        return null;   // remove this line
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    // Use custom authentication system for the verification of the
    // passed username and password.  (Here we are just faking it.)
    private boolean checkCustomAuthenticationSystem(String username, String password) {
        return username.equals("spring") && password.equals("spring");
    }
}
