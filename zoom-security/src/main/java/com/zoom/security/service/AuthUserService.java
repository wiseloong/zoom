package com.zoom.security.service;

import com.zoom.security.model.Person;

public interface AuthUserService {

    Person loadUserByUsername(String username);
}
