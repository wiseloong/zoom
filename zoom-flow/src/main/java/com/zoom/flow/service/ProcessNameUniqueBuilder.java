package com.zoom.flow.service;

import com.zoom.flow.util.FlowElementGenerator;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.ServiceTask;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.cfg.IdGenerator;
import org.flowable.common.engine.impl.persistence.StrongUuidGenerator;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Slf4j
public class ProcessNameUniqueBuilder extends ProcessBuilder implements Serializable {

    private static final long serialVersionUID = -8338160531188684087L;

    private final IdGenerator idGenerator;

    public ProcessNameUniqueBuilder(String id, String name, IdGenerator idGenerator) {
        super(id, name);
        if (idGenerator == null) {
            this.idGenerator = new StrongUuidGenerator();
        } else {
            this.idGenerator = idGenerator;
        }
    }

    public ProcessNameUniqueBuilder(Process p, IdGenerator idGenerator) {
        super(p);
        if (idGenerator == null) {
            this.idGenerator = new StrongUuidGenerator();
        } else {
            this.idGenerator = idGenerator;
        }
    }

    private String findElementId(String name) {
        Collection<FlowElement> flowElements = process.getFlowElements();
        FlowElement f = flowElements.stream()
                .filter(o -> Objects.equals(name, o.getName()))
                .findAny().orElse(null);
        return (f == null) ? null : f.getId();
    }

    private String uuid() {
        return FlowElementGenerator.START_ID + idGenerator.getNextId();
    }

    public ProcessNameUniqueBuilder startEvent() {
        super.startEvent();
        return this;
    }

    public ProcessNameUniqueBuilder startUserTask(String name) {
        super.startUserTask(name);
        return this;
    }

    /**
     * 创建一个结束节点，并放到流程对象里，针对单个结束节点流程
     */
    public ProcessNameUniqueBuilder endEvent() {
        super.endEvent();
        return this;
    }

    /**
     * 创建一个结束节点，并放到流程对象里，针对多个结束节点流程
     *
     * @param name 结束节点唯一名称
     */
    public ProcessNameUniqueBuilder endEvent(String name) {
        super.endEvent(uuid(), name);
        return this;
    }

    /**
     * 创建一个排他网关，并放到流程对象里
     *
     * @param name 排他网关id
     */
    public ProcessNameUniqueBuilder exclusiveGateway(String name) {
        super.exclusiveGateway(uuid(), name);
        return this;
    }

    /**
     * 创建一个用户节点，并放到流程对象里
     *
     * @param name 用户节点名称
     * @param user 用户节点指定用户
     */
    public ProcessNameUniqueBuilder userTask(String name, String user) {
        UserTask element = FlowElementGenerator.userTask(uuid(), name);
        element.setAssignee(user);
        process.addFlowElement(element);
        return this;
    }

    public ProcessNameUniqueBuilder serviceTask(String name) {
        ServiceTask element = FlowElementGenerator.serviceTask(uuid(), name);
        process.addFlowElement(element);
        return this;
    }

    /**
     * 创建一个结束连线，并放到流程对象里，仅单结束节点使用
     *
     * @param sourceName 与结束节点连接的节点名称，这个节点必须先创建
     */
    public ProcessNameUniqueBuilder endSequenceFlow(String sourceName) {
        String id = findElementId(sourceName);
        super.sequenceFlow(id, FlowElementGenerator.END_EVENT);
        return this;
    }

    /**
     * 创建一个带判断的连线，并放到流程对象里
     *
     * @param sourceName 开始端名称，这个节点必须先创建
     * @param targetName 结束端名称，这个节点必须先创建
     */
    public ProcessNameUniqueBuilder sequenceFlow(String sourceName, String targetName) {
        String sourceId = findElementId(sourceName);
        String targetId = findElementId(targetName);
        super.sequenceFlow(sourceId, targetId);
        return this;
    }

    /**
     * 创建一个带判断的连线，并放到流程对象里
     *
     * @param sourceName          开始端名称，这个节点必须先创建
     * @param targetName          结束端名称，这个节点必须先创建
     * @param conditionExpression 判断逻辑
     */
    public ProcessNameUniqueBuilder sequenceFlow(String sourceName, String targetName, String conditionExpression) {
        String sourceId = findElementId(sourceName);
        String targetId = findElementId(targetName);
        super.sequenceFlow(sourceId, targetId, conditionExpression);
        return this;
    }

    /**
     * 创建开始节点以及开始节点与第一个节点的连线，必须放在最后执行
     *
     * @param startTaskName 第一个节点名称，这个节点必须先创建
     * @return 流程对象
     */
    public ProcessNameUniqueBuilder endStart(String startTaskName) {
        String id = findElementId(startTaskName);
        startEvent();
        super.sequenceFlow(FlowElementGenerator.START_EVENT, id);
        return this;
    }

    /**
     * 创建开始节点以及启动用户节点与第一个节点的连线，必须放在最后执行
     *
     * @param startUserTaskName 开始用户节点中文名称
     * @param startElementName  与开始用户节点相连的第一个节点名称，这个节点必须先创建
     * @return 流程对象
     */
    public ProcessNameUniqueBuilder endStartUser(String startUserTaskName, String startElementName) {
        String id = findElementId(startElementName);
        startEvent();
        startUserTask(startUserTaskName);
        super.sequenceFlow(FlowElementGenerator.START_USER_TASK, id);
        return this;
    }

    public ProcessNameUniqueBuilder endBuild() {
        super.outgoingFlows();
        return this;
    }
}
