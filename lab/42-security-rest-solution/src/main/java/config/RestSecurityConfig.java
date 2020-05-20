package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableWebSecurity  // Redundant in Spring Boot app
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/accounts/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                .mvcMatchers(HttpMethod.PUT, "/accounts/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .mvcMatchers(HttpMethod.POST, "/accounts/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/accounts/**").hasAnyRole("SUPERADMIN")
                .mvcMatchers("/**").authenticated()
                .and()
            .httpBasic()
                .and()
            .csrf().disable();
        // @formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()
            .withUser("user").password(passwordEncoder.encode("user")).roles("USER").and()
            .withUser("admin").password(passwordEncoder.encode("admin")).roles("USER", "ADMIN").and()
            .withUser("superadmin").password(passwordEncoder.encode("superadmin")).roles("USER", "ADMIN", "SUPERADMIN");

        // Add authentication based upon the custom UserDetailsService
        auth.userDetailsService(new CustomUserDetailsService(passwordEncoder));

        // Add authentication based upon the custom AuthenticationProvider
        auth.authenticationProvider(new CustomAuthenticationProvider());

    }

}

class CustomUserDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User.UserBuilder builder = User.builder();
        builder.username(username);
        builder.password(passwordEncoder.encode(username));
        switch (username) {
            case "mary":
                builder.roles("USER");
                break;
            case "joe":
                builder.roles("USER", "ADMIN");
                break;
            default:
                throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }
}

class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (checkCustomAuthenticationSystem(username, password)) {

            return new UsernamePasswordAuthenticationToken(
                    username, password, new ArrayList<>(Arrays.asList(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return "ROLE_ADMIN";
                }
            })));
        } else {
            return null;
        }
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
