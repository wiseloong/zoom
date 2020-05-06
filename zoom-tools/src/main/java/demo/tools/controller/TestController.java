package demo.tools.controller;

import com.zoom.tools.api.Assert;
import com.zoom.tools.jdbc.JdbcUtils;
import com.zoom.tools.jdbc.TargetDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void abc() {
//        Map<String, Object> a = jdbcTemplate.queryForMap("select  * from cm_person where id =1");
//        System.out.println(a);
        JdbcUtils.getById("a", 1L);
    }

    @TargetDataSource("slave")
    public void bcd() {
//        Map<String, Object> a = jdbcTemplate.queryForMap("select  * from sm_role where id =1");
//        System.out.println(a);
        JdbcUtils.getById("a", 1L);
    }
}
