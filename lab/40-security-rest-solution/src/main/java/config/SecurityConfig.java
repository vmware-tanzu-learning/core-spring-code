package config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .mvcMatchers(HttpMethod.GET, "/accounts/*").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
            .mvcMatchers(HttpMethod.GET, "/accounts").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
            .mvcMatchers(HttpMethod.GET, "/accounts/*/beneficiaries").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
            .mvcMatchers(HttpMethod.GET, "/accounts/*/beneficiaries/*").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
            .mvcMatchers(HttpMethod.PUT, "/accounts/*").hasAnyRole("ADMIN", "SUPERADMIN")
            .mvcMatchers(HttpMethod.PUT, "/accounts/*/beneficiaries/*").hasAnyRole("ADMIN", "SUPERADMIN")
            .mvcMatchers(HttpMethod.POST, "/accounts").hasAnyRole("ADMIN", "SUPERADMIN")
            .mvcMatchers(HttpMethod.POST, "/accounts/*/beneficiaries").hasAnyRole("ADMIN", "SUPERADMIN")
            .mvcMatchers(HttpMethod.DELETE, "/accounts/**").hasAnyRole("SUPERADMIN")
            .mvcMatchers("/**").authenticated()
            .and()
            .httpBasic()
            .and()
            .csrf().disable();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//        auth.inMemoryAuthentication()
//            .withUser("user").password(passwordEncoder.encode("user")).roles("USER").and()
//            .withUser("admin").password(passwordEncoder.encode("admin")).roles("USER", "ADMIN").and()
//            .withUser("superadmin").password(passwordEncoder.encode("superadmin")).roles("USER", "ADMIN", "SUPERADMIN");
//
//    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider
                = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(new CustomUserDetailsService());
        return daoAuthenticationProvider;
    }
}

class CustomUserDetailsService implements UserDetailsService {

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User.UserBuilder builder = User.builder();
        builder.username(username);
        builder.password(passwordEncoder.encode(username));
        switch (username) {
            case "user":
                builder.roles("USER");
                break;
            case "admin":
                builder.roles("USER", "ADMIN");
                break;
            case "superadmin":
                builder.roles("USER", "ADMIN", "SUPERADMIN");
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

class UserNameNotFoundExeption extends RuntimeException {

}
