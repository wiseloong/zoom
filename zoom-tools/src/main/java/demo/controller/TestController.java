package demo.controller;

import com.zoom.tools.api.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/abc")
    public String abc() {
        Object a = null;
        Assert.state(null == a, "测试一个错误！");
        return "abc";
    }

    @GetMapping("/bcd")
    public String bcd() {
        Object a = null;
        Assert.notNull(a);
        return "bcd";
    }
}
