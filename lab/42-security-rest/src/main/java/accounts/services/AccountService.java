package accounts.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    // TODO-09: Add method security annotation to a method
    // - Uncomment and complete PreAuthorize annotation below
    //   so that the method is permitted to be invoked only
    //   when both of following two run-time conditions are met:
    //   (Use SpEL to specify these conditions.)
    //
    //   (a) the logged-in user belongs to "ADMIN" role
    //   (b) the value of the "username" argument matches
    //       the value of the logged-in principal's
    //       username, which can be accessed as
    //       principal.username or authentication.name.
    //
    //@PreAuthorize(/* Add code here */)
    public List<String> getAuthoritiesForUser(String username) {

        // TODO-08: Retrieve authorities (roles) for the logged-in user
        // (This is probably not a typical business logic you will
        //  have in a service layer. This is mainly to show
        //  how SecurityContext object is maintained in the local
        //  thread, which can be accessed via SecurityContextHolder)
        // - Replace null below with proper code - use SecurityContextHolder
        // - Restart the application (or let Spring Boot Devtools to restart the app)
        // - Using Chrome Incognito browser or "curl", access
        //   http://localhost:8080/authorities?username=<username>
        // - Verify that roles of the logged-in user get displayed
        Collection<? extends GrantedAuthority> grantedAuthorities
                = null; // Modify this line

        return grantedAuthorities.stream()
                                 .map(GrantedAuthority::getAuthority)
                                 .collect(Collectors.toList());
    }

}
