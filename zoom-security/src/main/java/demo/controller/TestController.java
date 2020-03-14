package demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/abc")
    public String abc() {
        return "abc";
    }

    @GetMapping("/bcd")
    public String bcd() {
        return "bcd";
    }

}
