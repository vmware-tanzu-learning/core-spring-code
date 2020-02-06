package accounts.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AccountService {

    // TODO-10: Add method-level security to a method
    // - Add a proper annotation to this method so that it gets
    //   invoked only if the logged-in user belongs to "ADMIN"
    //   role AND the value of the "username" request parameter
    //   of the request URL matches the value of the principal's
    //   username

    public List<String> getAuthoritiesForUser(String username) {

        // TODO-09: Retrieve authorities for the logged-in user
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
