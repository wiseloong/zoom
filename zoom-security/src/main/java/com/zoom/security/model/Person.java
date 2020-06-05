package com.zoom.security.model;

import com.zoom.tools.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class Person extends BaseEntity implements UserDetails, CredentialsContainer {
    private static final long serialVersionUID = -6409251271057743027L;
    private String password;

    private Set<Role> roles;
    private Set<Permission> permissions;
    private Set<Resource> resources;

    @Override
    public void eraseCredentials() {
        password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(o -> new SimpleGrantedAuthority(o.getCode())).collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return super.code;
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
    public boolean isEnabled() {
        return (1 == super.valid);
    }
}
