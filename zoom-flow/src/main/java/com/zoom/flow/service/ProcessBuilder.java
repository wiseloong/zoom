package com.zoom.flow.service;

import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ProcessBuilder {

    protected final Process process = new Process();
    protected final List<SequenceFlow> sequenceFlows = new ArrayList<>();
    protected final List<ExclusiveGateway> exclusiveGateways = new ArrayList<>();

    public static final String START_EVENT = "startEvent";
    public static final String END_EVENT = "endEvent";
    public static final String START_USER_TASK = "startUserTask";

    public ProcessBuilder(String id, String name) {
        process.setId(id);
        process.setName(name);
    }

    /**
     * 创建一个开始节点，并放到流程对象里
     */
    abstract ProcessBuilder startEvent();

    /**
     * 创建一个启动用户节点，并放到流程对象里
     *
     * @param name 启动用户节点中文名称
     */
    abstract ProcessBuilder startUserTask(String name);

    /**
     * 创建一个结束节点，并放到流程对象里，针对多个结束节点流程
     *
     * @param key 结束节点唯一标示（id或唯一name）
     */
    abstract ProcessBuilder endEvent(String key);

    abstract ProcessBuilder endBuild();

    protected StartEvent newStartEvent() {
        StartEvent element = new StartEvent();
        element.setId(START_EVENT);
        element.setName(START_EVENT);
        process.addFlowElement(element);
        return element;
    }

    protected EndEvent newEndEvent() {
        return newEndEvent(END_EVENT, END_EVENT);
    }

    protected EndEvent newEndEvent(String id, String name) {
        EndEvent element = new EndEvent();
        element.setId(id);
        element.setName(name);
        process.addFlowElement(element);
        return element;
    }

    protected UserTask newStartUserTask(String name) {
        UserTask element = newUserTask(START_USER_TASK, name);
//        element.setAssignee("${initiator}");
        element.setAssignee("admin");
        newSequenceFlow(START_EVENT, START_USER_TASK);
        return element;
    }

    protected UserTask newUserTask(String id, String name) {
        UserTask element = new UserTask();
        element.setId(id);
        element.setName(name);
        process.addFlowElement(element);
        return element;
    }

    protected ServiceTask newServiceTask(String id, String name) {
        ServiceTask element = new ServiceTask();
        element.setId(id);
        element.setName(name);
        process.addFlowElement(element);
        return element;
    }

    protected SequenceFlow newSequenceFlow(String sourceRef, String targetRef) {
        SequenceFlow element = new SequenceFlow(sourceRef, targetRef);
        process.addFlowElement(element);
        return element;
    }

    protected SequenceFlow newSequenceFlow(String sourceRef, String targetRef, String conditionExpression) {
        SequenceFlow element = newSequenceFlow(sourceRef, targetRef);
        if (StringUtils.hasText(conditionExpression)) {
            element.setConditionExpression(conditionExpression);
            sequenceFlows.add(element);
        }
        return element;
    }

    protected ExclusiveGateway exclusiveGateway(String id, String name) {
        ExclusiveGateway element = new ExclusiveGateway();
        element.setId(id);
        element.setName(name);
        exclusiveGateways.add(element);
        process.addFlowElement(element);
        return element;
    }

    /**
     * 排他网关关联outgoingFlows，放在最后的生成逻辑里
     */
    protected void outgoingFlows() {
        exclusiveGateways.forEach(eg -> {
            String id = eg.getId();
            List<SequenceFlow> list = sequenceFlows.stream()
                    .filter(o -> Objects.equals(id, o.getSourceRef()) || Objects.equals(id, o.getTargetRef()))
                    .collect(Collectors.toList());
            eg.setOutgoingFlows(list);
        });
    }

    public Process process() {
        return process;
    }
}
