package com.zoom.flow.service;

import com.zoom.flow.util.FlowElementGenerator;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ProcessBuilder {

    protected final Process process;

    public ProcessBuilder(String id, String name) {
        process = new Process();
        process.setId(id);
        process.setName(name);
    }

    public ProcessBuilder(Process p) {
        process = p;
    }

    protected ProcessBuilder startEvent() {
        StartEvent element = FlowElementGenerator.startEvent();
        process.addFlowElement(element);
        return this;
    }

    protected ProcessBuilder endEvent() {
        EndEvent element = FlowElementGenerator.endEvent();
        process.addFlowElement(element);
        return this;
    }

    protected ProcessBuilder endEvent(String id, String name) {
        EndEvent element = FlowElementGenerator.endEvent(id, name);
        process.addFlowElement(element);
        return this;
    }

    protected ProcessBuilder startUserTask(String name) {
        UserTask element = FlowElementGenerator.startUserTask(name);
        process.addFlowElement(element);
        return sequenceFlow(FlowElementGenerator.START_EVENT, FlowElementGenerator.START_USER_TASK);
    }

    protected ProcessBuilder sequenceFlow(String sourceRef, String targetRef) {
        SequenceFlow element = FlowElementGenerator.sequenceFlow(sourceRef, targetRef);
        process.addFlowElement(element);
        return this;
    }

    protected ProcessBuilder sequenceFlow(String sourceRef, String targetRef, String conditionExpression) {
        SequenceFlow element = FlowElementGenerator.sequenceFlow(sourceRef, targetRef, conditionExpression);
        process.addFlowElement(element);
        return this;
    }

    protected ProcessBuilder exclusiveGateway(String id, String name) {
        ExclusiveGateway element = FlowElementGenerator.exclusiveGateway(id, name);
        process.addFlowElement(element);
        return this;
    }

    /**
     * 排他网关关联outgoingFlows，放在最后的生成逻辑里
     */
    public void outgoingFlows() {
        List<FlowNode> flowNodes = process.findFlowElementsOfType(FlowNode.class, false);
        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class, false);
        for (FlowNode flowNode : flowNodes) {
            List<SequenceFlow> og = sequenceFlows.stream()
                    .filter(o -> Objects.equals(flowNode.getId(), o.getSourceRef()))
                    .collect(Collectors.toList());
            flowNode.setOutgoingFlows(og);
        }
    }

    public Process process() {
        return process;
    }
}
