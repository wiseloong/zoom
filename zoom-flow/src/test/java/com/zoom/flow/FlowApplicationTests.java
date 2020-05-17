package com.zoom.flow;

import com.zoom.flow.service.BpmnService;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.IdentityService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class FlowApplicationTests {

    @Autowired
    BpmnService bpmnService;
    @Autowired
    TaskService taskService;
    @Autowired
    IdentityService identityService;

    @Test
    void newIdFlow() {
        identityService.setAuthenticatedUserId("admin");
        Process process = bpmnService.createProcessIdUnique("qingjia", "请假")
                .startUser("请假", "flow_2")
                .exclusiveGateway("flow_2")
                .sequenceFlow("flow_2", "flow_3", "${approved}")
                .sequenceFlow("flow_2", "flow_4", "${!approved}")
                .userTask("flow_3", "经理审批", "admin")
                .userTask("flow_4", "总裁审批", "admin")
                .endSequenceFlow("flow_3")
                .endSequenceFlow("flow_4")
                .endEvent().endBuild().process();
        Deployment deploy = bpmnService.deploy(process);
        System.out.println(deploy.toString());
    }

    @Test
    void newNameFlow() {
        identityService.setAuthenticatedUserId("admin");
        Process process = bpmnService.createProcessNameUnique("qingjia", "请假")
                .exclusiveGateway("网关")
                .userTask("经理审批", "admin")
                .userTask("总裁审批", "admin")
                .endEvent()
                .sequenceFlow("网关", "经理审批", "${approved}")
                .sequenceFlow("网关", "总裁审批", "${!approved}")
                .endSequenceFlow("经理审批")
                .endSequenceFlow("总裁审批")
                .endStartUser("请假", "网关")
                .endBuild().process();
        Deployment deploy = bpmnService.deploy(process);
        System.out.println(deploy.toString());
    }

    @Test
    void startFlow() {
        identityService.setAuthenticatedUserId("admin");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("approved", true);
        bpmnService.startUserFlow("qingjia", variables);
    }

    @Test
    void taskFlow() {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("admin").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
//            taskService.complete(tasks.get(i).getId());
        }

    }

}
