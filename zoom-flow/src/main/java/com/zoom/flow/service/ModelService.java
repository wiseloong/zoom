package com.zoom.flow.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zoom.flow.entity.AbstractModel;
import com.zoom.flow.repository.ModelMapper;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.cfg.IdGenerator;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class ModelService {

    public static final String USER_AUTO_PATCH = "auto_patch";  //默认自动编排构建用户

    private final ModelImageService modelImageService;
    private final ModelMapper modelMapper;
    private final IdGenerator idGenerator;

    public ModelService(ModelImageService modelImageService, ModelMapper modelMapper, IdGenerator idGenerator) {
        this.modelImageService = modelImageService;
        this.modelMapper = modelMapper;
        this.idGenerator = idGenerator;
    }

    public void saveModel(BpmnModel bpmnModel) {
        Process process = bpmnModel.getMainProcess();
        String modelId = process.getId();

        ObjectNode modelNode = new BpmnJsonConverter().convertToJson(bpmnModel);
        String modelNodeStr = modelNode.toString();

        byte[] thumbnail = modelImageService.generateThumbnailImage(modelId, modelNode);

        AbstractModel model = new AbstractModel();
        model.setKey(process.getId());
        model.setName(process.getName());
        model.setDescription(process.getDocumentation());
        model.setModelEditorJson(modelNodeStr);
        model.setThumbnail(thumbnail);
        model.setModelType(AbstractModel.MODEL_TYPE_BPMN);
        // model.setTenantId(model.getTenantId());

        List<AbstractModel> list = modelMapper.findByKeyAndType(modelId, AbstractModel.MODEL_TYPE_BPMN);
        if (list.size() > 0) {
            updateModel(model, list.get(0));
        } else {
            createModel(model);
        }
    }

    protected void updateModel(AbstractModel model, AbstractModel oldModel) {
        model.setId(oldModel.getId());
        model.setVersion(oldModel.getVersion() + 1);
        model.setLastUpdated(Calendar.getInstance().getTime());
        model.setLastUpdatedBy(USER_AUTO_PATCH);
        modelMapper.update(model);
    }

    protected void createModel(AbstractModel model) {
        model.setId(idGenerator.getNextId());
        model.setVersion(1);
        model.setCreated(Calendar.getInstance().getTime());
        model.setCreatedBy(USER_AUTO_PATCH);
        model.setLastUpdated(Calendar.getInstance().getTime());
        model.setLastUpdatedBy(USER_AUTO_PATCH);    //没有这个，页面编辑保存时报空指针
        modelMapper.insert(model);
    }
}
