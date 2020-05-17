package com.zoom.flow.service;

import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.ServiceTask;
import org.flowable.bpmn.model.UserTask;

import java.io.Serializable;

@Slf4j
public class ProcessIdUniqueBuilder extends ProcessBuilder implements Serializable {

    private static final long serialVersionUID = -2957843761170477772L;

    public ProcessIdUniqueBuilder(String id, String name) {
        super(id, name);
    }

    /**
     * 创建一个开始节点，并放到流程对象里
     */
    public ProcessIdUniqueBuilder startEvent() {
        super.newStartEvent();
        return this;
    }

    /**
     * 创建一个启动用户节点，并放到流程对象里
     *
     * @param name 启动用户节点中文名称
     */
    public ProcessIdUniqueBuilder startUserTask(String name) {
        super.newStartUserTask(name);
        return this;
    }

    /**
     * 创建一个结束节点，并放到流程对象里，针对单个结束节点流程
     */
    public ProcessIdUniqueBuilder endEvent() {
        super.newEndEvent();
        return this;
    }

    /**
     * 创建一个结束节点，并放到流程对象里，针对多个结束节点流程
     *
     * @param id 结束节点id
     */
    public ProcessIdUniqueBuilder endEvent(String id) {
        super.newEndEvent(id, END_EVENT);
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
        UserTask element = super.newUserTask(id, name);
        element.setAssignee(user);
        return this;
    }

    public ProcessIdUniqueBuilder serviceTask(String id, String name) {
        ServiceTask element = super.newServiceTask(id, name);
        return this;
    }

    /**
     * 创建一个结束连线，并放到流程对象里
     *
     * @param sourceId 与结束节点连接的节点id
     */
    public ProcessIdUniqueBuilder endSequenceFlow(String sourceId) {
        return sequenceFlow(sourceId, END_EVENT);
    }

    /**
     * 创建一个带判断的连线，并放到流程对象里
     *
     * @param sourceId 开始端id
     * @param targetId 结束端id
     */
    public ProcessIdUniqueBuilder sequenceFlow(String sourceId, String targetId) {
        newSequenceFlow(sourceId, targetId);
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
        newSequenceFlow(sourceId, targetId, conditionExpression);
        return this;
    }

    /**
     * 创建开始节点以及开始节点与第一个节点的连线
     *
     * @param startTaskId 第一个节点id
     * @return 流程对象
     */
    public ProcessIdUniqueBuilder start(String startTaskId) {
        super.newStartEvent();
        return sequenceFlow(START_EVENT, startTaskId);
    }

    /**
     * 创建开始节点以及启动用户节点与第一个节点的连线
     *
     * @param startUserTaskName 开始用户节点中文名称
     * @param startElementId    与开始用户节点相连的第一个节点id
     * @return 流程对象
     */
    public ProcessIdUniqueBuilder startUser(String startUserTaskName, String startElementId) {
        super.newStartEvent();
        super.newStartUserTask(startUserTaskName);
        return sequenceFlow(START_USER_TASK, startElementId);
    }

    public ProcessIdUniqueBuilder endBuild() {
        super.outgoingFlows();
        return this;
    }
}
