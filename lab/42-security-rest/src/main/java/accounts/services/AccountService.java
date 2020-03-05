package accounts.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AccountService {

    // TODO-09: Add method security annotation to a method
    // - Add a proper annotation to this method so that it is
    //   permitted to be invoked only when both of following
    //   two run-time conditions are met:
    //
    //   (a) the logged-in user belongs to "ADMIN" role
    //   (b) the value of the "username" argument matches
    //       the value of the logged-in principal's
    //       username, which can be accessed as
    //       principal.username or authentication.name.

    public List<String> getAuthoritiesForUser(String username) {

        // TODO-08: Retrieve authorities (roles) for the logged-in user
        // - Replace null below with proper code
        // - Restart the application
        // - Using Chrome Incognito browser or "curl", access
        //   http://localhost:8080/authorities?username=<username>
        // - Verify that roles of the logged-in user get displayed
        Collection<? extends GrantedAuthority> grantedAuthorities
                = null; // Modify this line

        List<String> authorities = new ArrayList<>();
        grantedAuthorities.stream().forEach(grantedAuthority -> {
            authorities.add(grantedAuthority.getAuthority());
        });
        return authorities;
    }

}
