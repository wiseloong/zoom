package com.zoom.flow.service;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.repository.Deployment;

/**
 * 流程编排能力接口
 */
public interface ArrangeService {

    /**
     * 根据流程标识获取流程模型
     *
     * @param processDefinitionKey 流程标识
     * @return 流程模型
     */
    BpmnModel getBpmnModel(String processDefinitionKey);

    /**
     * 根据流程标识获取流程定义
     *
     * @param processDefinitionKey 流程标识
     * @return 流程定义
     */
    Process getProcess(String processDefinitionKey);

    /**
     * 检查流程模型，不通过抛异常
     *
     * @param bpmnModel 流程模型
     */
    void validate(BpmnModel bpmnModel);

    /**
     * 检查流程定义，不通过抛异常
     *
     * @param process 流程定义
     */
    void validate(Process process);

    /**
     * 发布流程定义
     *
     * @param bpmnModel 流程模型
     */
    Deployment deploy(BpmnModel bpmnModel);

    /**
     * 发布流程定义
     *
     * @param process 流程定义
     */
    Deployment deploy(Process process);

    /**
     * 创建一个流程定义
     *
     * @param key  流程标识
     * @param name 流程名称
     * @return 流程定义
     */
    Process newProcess(String key, String name);

    /**
     * 创建一个启动流程，id、name为固定的startEvent
     *
     * @return 启动流程节点
     */
    FlowElement startEvent();

    /**
     * 针对单结束流程，创建一个结束流程，id，name为固定的endEvent
     *
     * @return 结束流程节点
     */
    FlowElement endEvent();

    /**
     * 针对多结束流程，创建一个结束流程，name为固定的endEvent
     *
     * @param id 节点id，可为空，自动创建id，注意：给null也与上面方法意义不同
     * @return 结束流程节点
     */
    FlowElement endEvent(String id);

    /**
     * 创建一个连线
     *
     * @param sourceId 开始节点id
     * @param targetId 结束节点id
     * @return 连线节点
     */
    FlowElement sequenceFlow(String sourceId, String targetId);

    /**
     * 创建一个带判断条件的连线
     *
     * @param sourceId            开始节点id
     * @param targetId            结束节点id
     * @param conditionExpression 判断条件
     * @return 连线节点
     */
    FlowElement sequenceFlow(String sourceId, String targetId, String conditionExpression);

    /**
     * 创建一个排他网关
     *
     * @param id   网关id，可为空，自动创建id
     * @param name 网关name（可为空）
     * @return 排他网关
     */
    FlowElement exclusiveGateway(String id, String name);

    /**
     * 创建一个并行网关
     *
     * @param id   网关id，可为空，自动创建id
     * @param name 网关name（可为空）
     * @return 并行网关
     */
    FlowElement parallelGateway(String id, String name);

    /**
     * 得到一个新的节点id
     *
     * @return 节点id
     */
    String uuid();
}
