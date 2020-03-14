package com.zoom.security.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * 系统打开是用来判断是否登陆，一般csrf需要这个
     */
    @GetMapping("/init")
    public void init() {
    }

    @PostMapping("/login")
    public String login() {
        return "asda";
    }
}
