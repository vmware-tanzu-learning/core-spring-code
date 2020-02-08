package accounts.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AccountService {

    @PreAuthorize("hasRole('ADMIN')   && " +
            "#username == authentication.name")
    public List<String> getAuthoritiesForUser(String username) {

        Collection<? extends GrantedAuthority> grantedAuthorities
                = SecurityContextHolder.getContext()
                                       .getAuthentication()
                                       .getAuthorities();

        List<String> authorities = new ArrayList<>();
        grantedAuthorities.stream().forEach(grantedAuthority -> {
            authorities.add(grantedAuthority.getAuthority());
        });
        return authorities;
    }

}
