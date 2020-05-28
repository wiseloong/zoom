package demo.security.controller;

import com.zoom.security.annotation.CurrentUser;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/abc")
    public String abc() {
        return "abc";
    }

    @RequestMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

    @GetMapping("/user2")
    public Principal user2(@CurrentUser Principal user) {
        BytesEncryptor a = Encryptors.stronger("password", "salt");
//        Authentication authentication =
//                SecurityContextHolder.getContext().getAuthentication();
//        CustomUser custom = (CustomUser) authentication == null ? null : authentication.getPrincipal();

        return user;
    }

    @GetMapping("/token")
    public Map<String, String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

    @GetMapping("/session")
    public Object session(HttpSession session) {
        return session.getAttribute("user");
    }

    @GetMapping("/get-session")
    public String getSessionId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessionId = "";
        Object temp = session.getAttribute("USER");//session中存储userCode的key为USER
        System.out.println(temp.toString());
        sessionId = session.getId();
        return sessionId;
    }

    @RequestMapping("/")
    @CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = {"x-auth-token", "x-requested-with", "x-xsrf-token"})
    public String home() {
        return "Hello World";
    }

}
