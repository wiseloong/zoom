package com.zoom.security.service.impl;

import com.zoom.security.mapper.AuthMapper;
import com.zoom.security.model.Permission;
import com.zoom.security.model.Person;
import com.zoom.security.service.AuthUserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthMapper authMapper;

    public AuthUserServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public Person loadUserByUsername(String username) {
        Person p = authMapper.findPersonByUsername(username);
        if (p != null) {
            Set<Permission> permissions = authMapper.findPermissionByPersonId(p.getRoles());
            p.setPermissions(permissions);
        }
        return p;
    }
}
