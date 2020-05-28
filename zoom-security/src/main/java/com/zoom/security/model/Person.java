package com.zoom.security.model;

import com.zoom.tools.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Person extends BaseEntity {
    private static final long serialVersionUID = -6409251271057743027L;
    private String password;

    private Set<Role> roles;
    private Set<Permission> permissions;
    private Set<Resource> resources;
}
