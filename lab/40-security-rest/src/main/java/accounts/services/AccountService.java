package accounts.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AccountService {

    // TODO-10: Add method-level security
    // - Add a proper annotation so that this method gets
    //   invoked if the logged-in user belongs to "ADMIN"
    //   role and the "username" request parameter value matches
    //   the value of the logged-in user's username

    public List<String> getAuthoritiesForUser(String username) {

        // TODO-09: Retrieve authorities for the logged-in user
        // - Replace null with proper code
        // - Restart the application
        // - Access http://localhost:8080/authorities?username=<username>
        //   using a browser or curl
        // - Verify that roles that a logged-in user belongs to get displayed
        Collection<? extends GrantedAuthority> grantedAuthorities
                = null; // Modify this line

        List<String> authorities = new ArrayList<>();
        grantedAuthorities.stream().forEach(grantedAuthority -> {
            authorities.add(grantedAuthority.getAuthority());
        });
        return authorities;
    }

}
