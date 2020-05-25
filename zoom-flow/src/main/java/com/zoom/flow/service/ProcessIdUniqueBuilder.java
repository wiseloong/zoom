package com.zoom.flow.service;

import com.zoom.flow.util.FlowElementGenerator;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.ServiceTask;
import org.flowable.bpmn.model.UserTask;

import java.io.Serializable;

@Slf4j
public class ProcessIdUniqueBuilder extends ProcessBuilder implements Serializable {

    private static final long serialVersionUID = -2957843761170477772L;

    public ProcessIdUniqueBuilder(String id, String name) {
        super(id, name);
    }

    public ProcessIdUniqueBuilder(Process p) {
        super(p);
    }

    /**
     * 创建一个开始节点，并放到流程对象里
     */
    public ProcessIdUniqueBuilder startEvent() {
        super.startEvent();
        return this;
    }

    /**
     * 创建一个启动用户节点，并放到流程对象里
     *
     * @param name 启动用户节点中文名称
     */
    public ProcessIdUniqueBuilder startUserTask(String name) {
        super.startUserTask(name);
        return this;
    }

    /**
     * 创建一个结束节点，并放到流程对象里，针对单个结束节点流程
     */
    public ProcessIdUniqueBuilder endEvent() {
        super.endEvent();
        return this;
    }

    /**
     * 创建一个结束节点，并放到流程对象里，针对多个结束节点流程
     *
     * @param id 结束节点id
     */
    public ProcessIdUniqueBuilder endEvent(String id) {
        EndEvent element = FlowElementGenerator.endEvent(id);
        process.addFlowElement(element);
        return this;
    }

    /**
     * 创建一个排他网关，并放到流程对象里
     *
     * @param id 排他网关id
     */
    public ProcessIdUniqueBuilder exclusiveGateway(String id) {
        super.exclusiveGateway(id, null);
        return this;
    }

    /**
     * 创建一个用户节点，并放到流程对象里
     *
     * @param id   用户节点id
     * @param name 用户节点名称
     * @param user 用户节点指定用户
     */
    public ProcessIdUniqueBuilder userTask(String id, String name, String user) {
        UserTask element = FlowElementGenerator.userTask(id, name);
        element.setAssignee(user);
        process.addFlowElement(element);
        return this;
    }

    public ProcessIdUniqueBuilder serviceTask(String id, String name) {
        ServiceTask element = FlowElementGenerator.serviceTask(id, name);
        process.addFlowElement(element);
        return this;
    }

    /**
     * 创建一个结束连线，并放到流程对象里
     *
     * @param sourceId 与结束节点连接的节点id
     */
    public ProcessIdUniqueBuilder endSequenceFlow(String sourceId) {
        return sequenceFlow(sourceId, FlowElementGenerator.END_EVENT);
    }

    /**
     * 创建一个带判断的连线，并放到流程对象里
     *
     * @param sourceId 开始端id
     * @param targetId 结束端id
     */
    public ProcessIdUniqueBuilder sequenceFlow(String sourceId, String targetId) {
        super.sequenceFlow(sourceId, targetId);
        return this;
    }

    /**
     * 创建一个带判断的连线，并放到流程对象里
     *
     * @param sourceId            开始端id
     * @param targetId            结束端id
     * @param conditionExpression 判断逻辑
     */
    public ProcessIdUniqueBuilder sequenceFlow(String sourceId, String targetId, String conditionExpression) {
        super.sequenceFlow(sourceId, targetId, conditionExpression);
        return this;
    }

    /**
     * 创建开始节点以及开始节点与第一个节点的连线
     *
     * @param startTaskId 第一个节点id
     * @return 流程对象
     */
    public ProcessIdUniqueBuilder start(String startTaskId) {
        startEvent();
        return sequenceFlow(FlowElementGenerator.START_EVENT, startTaskId);
    }

    /**
     * 创建开始节点以及启动用户节点与第一个节点的连线
     *
     * @param startUserTaskName 开始用户节点中文名称
     * @param startElementId    与开始用户节点相连的第一个节点id
     * @return 流程对象
     */
    public ProcessIdUniqueBuilder startUser(String startUserTaskName, String startElementId) {
        startEvent();
        startUserTask(startUserTaskName);
        return sequenceFlow(FlowElementGenerator.START_USER_TASK, startElementId);
    }

    public ProcessIdUniqueBuilder endBuild() {
        super.outgoingFlows();
        return this;
    }
}
