package config;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// TODO-04: Enable web security
// - Note that you are going to configure Spring security
//   (authentication and authorization) through this class
// - Add an appropriate annotation to this class
// - Note that this class extends WebSecurityConfigurerAdapter class

// TODO-11: Enable global method security
// - Add an appropriate annotation to this class
// - Make sure "prePostEnabled" and "jsr250Enabled"
//   attributes are set to true

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

        // TODO-06: Add three users with corresponding roles:
        // - "user"/"user" with "USER" role
        // - "admin"/"admin" with "USER" and "ADMIN" roles
        // - "superadmin"/"superadmin" with "USER", "ADMIN", and "SUPERADMIN" roles
        auth.inMemoryAuthentication()

        ;

        // TODO-16: Add a custom authentication provider
        // - Note that this custom authentication provider is used
        //   in addition to the authentication provider that is
        //   already configured
        // - Uncomment the line below and finish up the code
        //auth.

    }

    // TODO-15: Add a method that returns a DaoAuthenticationProvider
    // - Uncomment the code below
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider
                = new DaoAuthenticationProvider();
        //daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        //daoAuthenticationProvider.setUserDetailsService(new CustomUserDetailsService(passwordEncoder));
        return daoAuthenticationProvider;
    }
}

// TODO-14: Create custom UserDetailsService
// - Note that it needs to implement loadUserByUsername method
//   of the UserDetailsService interface
// - Uncomment the code fragment below so that this custom
//   UserDetailsService maintains UserDetails of two users:
//   "mary"/"mary" with "USER" role and
//   "joe"/"joe" with "USER" and "ADMIN" role
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
