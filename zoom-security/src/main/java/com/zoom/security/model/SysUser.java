package com.zoom.security.model;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class SysUser extends Person implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = -1394106965655569252L;
    private final Set<GrantedAuthority> authorities;
    private final boolean enabled;

    public SysUser(Set<GrantedAuthority> authorities, boolean enabled) {
        this.authorities = authorities;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        setPassword(null);
    }
}
