package com.zoom.flow.service;

import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.cfg.IdGenerator;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BpmnService {

    final RuntimeService runtimeService;
    final RepositoryService repositoryService;
    final TaskService taskService;
    final IdGenerator idGenerator;

    public BpmnService(RuntimeService runtimeService, RepositoryService repositoryService,
                       TaskService taskService, IdGenerator idGenerator) {
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.taskService = taskService;
        this.idGenerator = idGenerator;
    }

    /**
     * 创建一个流程模型
     *
     * @param process 流程
     */
    public BpmnModel startBpmn(Process process) {
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.addProcess(process);
        return bpmnModel;
    }

    /**
     * 流程模型检查
     *
     * @param bpmnModel 流程模型
     */
    public void validate(BpmnModel bpmnModel) {
        ProcessValidator validator = new ProcessValidatorFactory().createDefaultProcessValidator();
        List<ValidationError> errors = validator.validate(bpmnModel);
        if (errors.size() > 0) {
            throw new RuntimeException("流程有误，请检查后重试");
        }
    }

    /**
     * 发布流程
     *
     * @param processId 流程id
     * @param bpmnModel 流程模型
     * @return 发布结果
     */
    public Deployment deploy(String processId, BpmnModel bpmnModel) {
        validate(bpmnModel);
        final String fileName = "flow_" + processId + "_bpmn20.xml";
        new BpmnAutoLayout(bpmnModel).execute();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .addBpmnModel(fileName, bpmnModel);
        return deploymentBuilder.deploy();
    }

    /**
     * 发布流程
     *
     * @param process 流程对象
     * @return 发布结果
     */
    public Deployment deploy(Process process) {
        String id = process.getId();
        return deploy(id, startBpmn(process));
    }

    /**
     * 启动一个流程
     *
     * @param processKey 流程key
     */
    public ProcessInstance start(String processKey) {
        return runtimeService.startProcessInstanceByKey(processKey);
    }

    /**
     * 启动一个用户流程
     *
     * @param processKey 流程key
     * @param variables  表单数据
     */
    public void startUserFlow(String processKey, Map<String, Object> variables) {
        ProcessInstance processInstance = start(processKey);
        String id = processInstance.getId();
        Task task = taskService.createTaskQuery().processInstanceId(id).list().get(0);
        taskService.complete(task.getId(), variables);
    }

    public ProcessIdUniqueBuilder createProcessIdUnique(String id, String name) {
        return new ProcessIdUniqueBuilder(id, name);
    }

    public ProcessNameUniqueBuilder createProcessNameUnique(String id, String name) {
        return new ProcessNameUniqueBuilder(id, name, idGenerator);
    }

}
