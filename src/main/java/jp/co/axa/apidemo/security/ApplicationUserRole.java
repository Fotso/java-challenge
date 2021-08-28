package jp.co.axa.apidemo.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static jp.co.axa.apidemo.security.ApplicationUserPermission.*;

/**
 * ApplicationUserRole
 * Enum class that defines a user role.
 */
public enum ApplicationUserRole {

    USER(Sets.newHashSet(EMPLOYEE_READ)),
    ADMIN(Sets.newHashSet(EMPLOYEE_EDIT, EMPLOYEE_READ, EMPLOYEE_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermission() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermission().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority ("ROLE_" + this.name()));
        return permissions;
    }
}
