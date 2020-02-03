package config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()
            .withUser("user").password(passwordEncoder.encode("user")).roles("USER").and()
            .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN", "SUPERUSER").and()
            .withUser("superadmin").password(passwordEncoder.encode("superadmin")).roles("USER", "ADMIN", "SUPERADMIN");

    }
}
