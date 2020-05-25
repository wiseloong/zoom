package com.zoom.flow.util;

import org.flowable.bpmn.model.*;
import org.springframework.util.StringUtils;

public class FlowElementGenerator {

    public static final String START_EVENT = "startEvent";
    public static final String END_EVENT = "endEvent";
    public static final String START_USER_TASK = "startUserTask";
    public static final String START_ID = "sid-";  //id不能以某些字符开头，比如数字，需要给id传固定的开头

    public static StartEvent startEvent() {
        StartEvent element = new StartEvent();
        element.setId(START_EVENT);
        element.setName(START_EVENT);
        // 为了统一画图流程，这里统一通过代码实现启动到第一个执行用户时加入人员
        // element.setInitiator("initiator");
        return element;
    }

    /**
     * 针对单个结束节点流程，创建一个结束节点
     */
    public static EndEvent endEvent() {
        return endEvent(END_EVENT, END_EVENT);
    }

    /**
     * 针对多个结束节点流程，创建一个结束节点，需要不同的id来区分
     *
     * @param id 结束节点id
     */
    public static EndEvent endEvent(String id) {
        return endEvent(id, END_EVENT);
    }

    public static EndEvent endEvent(String id, String name) {
        EndEvent element = new EndEvent();
        element.setId(id);
        element.setName(name);
        return element;
    }

    /**
     * 创建一个发起人用户节点，方便流程回退给发起人
     *
     * @param name 启动用户节点名称
     */
    public static UserTask startUserTask(String name) {
        UserTask element = userTask(START_USER_TASK, name);
        // 为了统一画图流程，这里统一通过代码实现启动到第一步时加入发起人人员信息
        // element.setAssignee("${initiator}");
        return element;
    }

    public static UserTask userTask(String id, String name) {
        UserTask element = new UserTask();
        element.setId(id);
        element.setName(name);
        return element;
    }

    public static ServiceTask serviceTask(String id, String name) {
        ServiceTask element = new ServiceTask();
        element.setId(id);
        element.setName(name);
        return element;
    }

    public static SequenceFlow sequenceFlow(String sourceRef, String targetRef) {
        return new SequenceFlow(sourceRef, targetRef);
    }

    public static SequenceFlow sequenceFlow(String sourceRef, String targetRef, String conditionExpression) {
        SequenceFlow element = sequenceFlow(sourceRef, targetRef);
        if (StringUtils.hasText(conditionExpression)) {
            element.setConditionExpression(conditionExpression);
        }
        return element;
    }

    public static ExclusiveGateway exclusiveGateway(String id) {
        return exclusiveGateway(id, null);
    }

    public static ExclusiveGateway exclusiveGateway(String id, String name) {
        ExclusiveGateway element = new ExclusiveGateway();
        element.setId(id);
        element.setName(name);
        return element;
    }

    public static ParallelGateway parallelGateway(String id) {
        return parallelGateway(id, null);
    }

    public static ParallelGateway parallelGateway(String id, String name) {
        ParallelGateway element = new ParallelGateway();
        element.setId(id);
        element.setName(name);
        return element;
    }
}
