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
import org.flowable.engine.repository.ProcessDefinition;
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
     * @param process 流程定义
     */
    public BpmnModel newBpmn(Process process) {
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
     * 给流程定义里的元素节点添加去向线，应对流程检查和创建图形
     */
    public void outgoingFlows(Process process) {
        createProcessIdUnique(process).outgoingFlows();
    }

    /**
     * 给流程定义自动布局
     */
    public void autoLayout(BpmnModel bpmnModel) {
        new BpmnAutoLayout(bpmnModel).execute();
    }

    /**
     * 发布流程
     *
     * @param bpmnModel 流程模型
     * @return 发布结果
     */
    public Deployment deploy(BpmnModel bpmnModel) {
        Process p = bpmnModel.getMainProcess();
        String processKey = p.getId();
        String fileName = "flow_" + processKey + ".bpmn";
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .addBpmnModel(fileName, bpmnModel).key(processKey).name(p.getName());
        return deploymentBuilder.deploy();
    }

    /**
     * 发布流程
     *
     * @param process 流程定义
     * @return 发布结果
     */
    public Deployment deploy(Process process) {
        BpmnModel bpmnModel = newBpmn(process);
        return deploy(bpmnModel);
    }

    /**
     * 根据流程标识，获取最新的流程定义id
     *
     * @param processDefinitionKey 流程标识
     * @return 流程定义id
     */
    public String getProcessDefinitionId(String processDefinitionKey) {
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
        return pd.getId();
    }

    /**
     * 根据流程标识，获取流程模型
     *
     * @param processDefinitionKey 流程标识
     * @return 流程模型
     */
    public BpmnModel findBpmnModel(String processDefinitionKey) {
        String id = getProcessDefinitionId(processDefinitionKey);
        return repositoryService.getBpmnModel(id);
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

    public ProcessIdUniqueBuilder createProcessIdUnique(Process process) {
        return new ProcessIdUniqueBuilder(process);
    }

    public ProcessNameUniqueBuilder createProcessNameUnique(String id, String name) {
        return new ProcessNameUniqueBuilder(id, name, idGenerator);
    }

    public ProcessNameUniqueBuilder createProcessNameUnique(Process process) {
        return new ProcessNameUniqueBuilder(process, idGenerator);
    }

}
