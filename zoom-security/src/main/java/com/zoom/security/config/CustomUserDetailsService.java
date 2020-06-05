package com.zoom.security.config;

import com.zoom.security.service.AuthUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserService authUserService;

    public CustomUserDetailsService(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = authUserService.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("找不到该用户！");
        }
        return user;
    }
}
