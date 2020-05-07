package com.zoom.security.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * 系统打开是用来判断是否登陆，一般csrf需要这个
     */
    @GetMapping("/init")
    public void init() {
    }

    /**
     * 系统打开是用来判断是否登陆，一般csrf需要这个
     */
    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

}
