package accounts.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @PreAuthorize("hasRole('ADMIN') && #username == principal.username")
    public List<String> getAuthoritiesForUser(String username) {

        Collection<? extends GrantedAuthority> grantedAuthorities
                = SecurityContextHolder.getContext()
                                       .getAuthentication()
                                       .getAuthorities();

        return grantedAuthorities.stream()
                                 .map(GrantedAuthority::getAuthority)
                                 .collect(Collectors.toList());
    }

}
