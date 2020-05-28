package com.zoom.security;

import com.zoom.security.mapper.AuthMapper;
import com.zoom.security.model.Permission;
import com.zoom.security.model.Person;
import demo.security.SecurityApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootTest(classes = SecurityApplication.class)
class SecurityApplicationTests {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
//        System.out.println(passwordEncoder.encode("111111"));
        Person p = authMapper.findPersonByUsername("root");
        Set<Permission> b = authMapper.findPermissionByPersonId(p.getRoles());
        System.out.println(p.toString());
    }

}
