package com.bluethinkInc.authentication.authorization_service.model;

import com.bluethinkInc.authentication.authorization_service.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * UserPrincipal represents the authenticated user details used by Spring Security.
 * It is built from the {@link User} entity and exposes the information required
 * by the framework (username, password, authorities and account state flags).
 */
public class UserPrincipal implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String name;
    private final String email; // used as username
    private final String phone;
    private final Role role;
    private final boolean active;

    public UserPrincipal(Long id, String name, String email, String phone, Role role, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.active = active;
    }

    public static UserPrincipal build(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getEmail(),
//                user.getPassword(),
                user.getPhone(),
                user.getRole(),
                Boolean.TRUE.equals(user.getIsActive())
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map enum role to a single granted authority (e.g. ROLE_CUSTOMER)
        String roleName = (role == null) ? "ROLE_USER" : "ROLE_" + role.name();
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        // Use email as the principal username
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // adapt if you add expiry logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // adapt if you add lock/unlock logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // adapt if you add credential expiry
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
