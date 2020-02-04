package config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// TODO-04: Enable web security
// - Add an appropriate annotation to this class
// - Note that this class extends WebSecurityConfigurerAdapter class

// TODO-11: Enable method level security

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            // TODO-05: Configure authorization
            // - Allow only "SUPERADMIN" role can perform "delete" operation
            //   against account and beneficiary
            // - Allow only "ADMIN" or "SUPERADMIN" role can perform "post"
            //   or "put" operations against account and beneficiary
            // - Allow all roles - "USER", "ADMIN", "SUPERADMIN" - can
            //   perform "get" operation against account and beneficiary

            // For all other methods and URL's, make sure the caller is authenticated
            .mvcMatchers("/**").authenticated()
            .and()
            .httpBasic()
            .and()
            .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // TODO-06: Add three users with corresponding roles:
        // - "user"/"user" with "USER" role
        // - "admin"/"admin" with "USER" and "ADMIN" roles
        // - "superadmin"/"superadmin" with "USER", "ADMIN", and "SUPERADMIN" roles
        auth.inMemoryAuthentication()

        ;


    }
}
