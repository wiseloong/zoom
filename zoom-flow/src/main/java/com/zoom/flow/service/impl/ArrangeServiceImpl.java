package com.zoom.flow.service.impl;

import com.zoom.flow.service.ArrangeService;
import com.zoom.flow.service.BpmnService;
import com.zoom.flow.service.ModelService;
import com.zoom.flow.util.FlowElementGenerator;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.cfg.IdGenerator;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArrangeServiceImpl implements ArrangeService {

    @Autowired
    private BpmnService bpmnService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private IdGenerator idGenerator;

    @Override
    public BpmnModel getBpmnModel(String processDefinitionKey) {
        return bpmnService.findBpmnModel(processDefinitionKey);
    }

    @Override
    public Process getProcess(String processDefinitionKey) {
        BpmnModel bpmnModel = bpmnService.findBpmnModel(processDefinitionKey);
        return bpmnModel.getMainProcess();
    }

    @Override
    public void validate(BpmnModel bpmnModel) {
        Process process = bpmnModel.getMainProcess();
//        bpmnService.outgoingFlows(process);
        bpmnService.validate(bpmnModel);
    }

    @Override
    public void validate(Process process) {
//        bpmnService.outgoingFlows(process);
        BpmnModel bpmnModel = bpmnService.newBpmn(process);
        bpmnService.validate(bpmnModel);
    }

    @Override
    @Transactional
    public Deployment deploy(BpmnModel bpmnModel) {
        validate(bpmnModel);
        bpmnService.autoLayout(bpmnModel);
        modelService.saveModel(bpmnModel);
        return bpmnService.deploy(bpmnModel);
    }

    @Override
    @Transactional
    public Deployment deploy(Process process) {
        BpmnModel bpmnModel = bpmnService.newBpmn(process);
        return deploy(bpmnModel);
    }

    @Override
    public Process newProcess(String key, String name) {
        Process process = new Process();
        process.setId(key);
        process.setName(name);
        return process;
    }

    @Override
    public FlowElement startEvent() {
        return FlowElementGenerator.startEvent();
    }

    @Override
    public FlowElement endEvent() {
        return FlowElementGenerator.endEvent();
    }

    @Override
    public FlowElement endEvent(String id) {
        if (id == null) {
            id = uuid();
        }
        return FlowElementGenerator.endEvent(id);
    }

    @Override
    public FlowElement sequenceFlow(String sourceId, String targetId) {
        return FlowElementGenerator.sequenceFlow(sourceId, targetId);
    }

    @Override
    public FlowElement sequenceFlow(String sourceId, String targetId, String conditionExpression) {
        return FlowElementGenerator.sequenceFlow(sourceId, targetId, conditionExpression);
    }

    @Override
    public FlowElement exclusiveGateway(String id, String name) {
        if (id == null) {
            id = uuid();
        }
        return FlowElementGenerator.exclusiveGateway(id, name);
    }

    @Override
    public FlowElement parallelGateway(String id, String name) {
        if (id == null) {
            id = uuid();
        }
        return FlowElementGenerator.parallelGateway(id, name);
    }

    @Override
    public String uuid() {
        return FlowElementGenerator.START_ID + this.idGenerator.getNextId();
    }
}
