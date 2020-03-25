package demo.tools.controller;

import com.zoom.tools.api.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/exc")
    public String exc() {
        Object a = null;
        Assert.state(null == a, "测试一个错误！");
        return "测试断言错误！";
    }

    @GetMapping("/setS")
    public String setSession(HttpSession session) {
        session.setAttribute("user", "javaboy");
        return "设置session成功！";
    }

    @GetMapping("/getS")
    public String getSession(HttpSession session) {
        return (String) session.getAttribute("user");
    }

}
