package accounts.security;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

//Optional exercise - Do the remaining steps only if you have extra time
//TODO-13 (Optional): Create custom UserDetailsService
//- Note that it needs to implement loadUserByUsername method
//of the UserDetailsService interface
//- Uncomment the commented code fragment below so that this custom
//UserDetailsService maintains UserDetails of two users:
//- "mary"/"mary" with "USER" role and
//- "joe"/"joe" with "USER" and "ADMIN" roles

//TODO-14a (Optional): Add authentication based upon the custom UserDetailsService
//- Annotate the class with @Component to make it a Spring manager bean

//TODO-18b (Optional): Remove the CustomUserDetailsService definition
// - Comment the @Component annotation added in a previous task

@Primary
public class CustomUserDetailsService implements UserDetailsService {

	private PasswordEncoder passwordEncoder;

	public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User.UserBuilder builder = User.builder();
//     builder.username(username);
//     builder.password(passwordEncoder.encode(username));
//     switch (username) {
//         case "mary":
//             builder.roles("USER");
//             break;
//         case "joe":
//             builder.roles("USER", "ADMIN");
//             break;
//         default:
//             throw new UsernameNotFoundException("User not found.");
//     }

		return builder.build();
	}
}
