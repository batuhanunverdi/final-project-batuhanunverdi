package org.patikadev.finalprojectbatuhanunverdi.security;

import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Mert Batuhan UNVERDI
 * @since 16.05.2022
 */
@RequiredArgsConstructor
public class UserDetail implements UserDetails {
    private final String username;
    private final String password;
    private final UserStatus userStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(userStatus.equals(UserStatus.LOCKED));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userStatus.equals(UserStatus.ACTIVE);
    }
}
