package accounts.security;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Component
@Primary
public class CustomUserDetailsService implements UserDetailsService {

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