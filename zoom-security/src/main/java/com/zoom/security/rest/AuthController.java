package com.zoom.security.rest;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
//@RequestMapping("/auth")
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

//    @PostMapping("/login")
//    public String login() {
//        return "asda";
//    }

    @GetMapping("/token")
    public Map<String, String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

    @RequestMapping("/")
    @CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token"})
    public String home() {
        return "Hello World";
    }

}
