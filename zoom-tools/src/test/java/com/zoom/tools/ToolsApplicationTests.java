package com.zoom.tools;

import demo.tools.ToolsApplication;
import demo.tools.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ToolsApplication.class)
class ToolsApplicationTests {

    @Autowired
    private TestController testController;

    @Test
    void contextLoads() {
        testController.abc();
        testController.bcd();
    }

}
