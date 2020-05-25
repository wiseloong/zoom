package com.zoom.flow.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zoom.flow.util.ImageGenerator;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ModelImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelImageService.class);

    private static float THUMBNAIL_WIDTH = 300f;

    protected BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    public byte[] generateThumbnailImage(String modelId, ObjectNode editorJsonNode) {
        try {

            BpmnModel bpmnModel = bpmnJsonConverter.convertToBpmnModel(editorJsonNode);

            double scaleFactor = 1.0;
            GraphicInfo diagramInfo = calculateDiagramSize(bpmnModel);
            if (diagramInfo.getWidth() > THUMBNAIL_WIDTH) {
                scaleFactor = diagramInfo.getWidth() / THUMBNAIL_WIDTH;
                scaleDiagram(bpmnModel, scaleFactor);
            }

            BufferedImage modelImage = ImageGenerator.createImage(bpmnModel, scaleFactor);
            if (modelImage != null) {
                return ImageGenerator.createByteArrayForImage(modelImage, "png");
            }
        } catch (Exception e) {
            LOGGER.error("Error creating thumbnail image {}", modelId, e);
        }
        return null;
    }

    protected void scaleDiagram(BpmnModel bpmnModel, double scaleFactor) {
        for (Pool pool : bpmnModel.getPools()) {
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            scaleGraphicInfo(graphicInfo, scaleFactor);
        }

        for (Process process : bpmnModel.getProcesses()) {
            scaleFlowElements(process.getFlowElements(), bpmnModel, scaleFactor);
            scaleArtifacts(process.getArtifacts(), bpmnModel, scaleFactor);
            for (Lane lane : process.getLanes()) {
                scaleGraphicInfo(bpmnModel.getGraphicInfo(lane.getId()), scaleFactor);
            }
        }
    }

    protected void scaleArtifacts(Collection<Artifact> artifactList, BpmnModel bpmnModel, double scaleFactor) {
        for (Artifact artifact : artifactList) {
            List<GraphicInfo> graphicInfoList = new ArrayList<>();
            if (artifact instanceof Association) {
                List<GraphicInfo> flowList = bpmnModel.getFlowLocationGraphicInfo(artifact.getId());
                if (flowList != null) {
                    graphicInfoList.addAll(flowList);
                }
            } else {
                graphicInfoList.add(bpmnModel.getGraphicInfo(artifact.getId()));
            }

            scaleGraphicInfoList(graphicInfoList, scaleFactor);
        }
    }

    protected void scaleFlowElements(Collection<FlowElement> elementList, BpmnModel bpmnModel, double scaleFactor) {
        for (FlowElement flowElement : elementList) {
            List<GraphicInfo> graphicInfoList = new ArrayList<>();
            if (flowElement instanceof SequenceFlow) {
                List<GraphicInfo> flowList = bpmnModel.getFlowLocationGraphicInfo(flowElement.getId());
                if (flowList != null) {
                    graphicInfoList.addAll(flowList);
                }

                // no graphic info for Data Objects
            } else if (!DataObject.class.isInstance(flowElement)) {
                graphicInfoList.add(bpmnModel.getGraphicInfo(flowElement.getId()));
            }

            scaleGraphicInfoList(graphicInfoList, scaleFactor);

            if (flowElement instanceof SubProcess) {
                SubProcess subProcess = (SubProcess) flowElement;
                scaleFlowElements(subProcess.getFlowElements(), bpmnModel, scaleFactor);
            }
        }
    }

    protected void scaleGraphicInfoList(List<GraphicInfo> graphicInfoList, double scaleFactor) {
        for (GraphicInfo graphicInfo : graphicInfoList) {
            scaleGraphicInfo(graphicInfo, scaleFactor);
        }
    }

    protected void scaleGraphicInfo(GraphicInfo graphicInfo, double scaleFactor) {
        graphicInfo.setX(graphicInfo.getX() / scaleFactor);
        graphicInfo.setY(graphicInfo.getY() / scaleFactor);
        graphicInfo.setWidth(graphicInfo.getWidth() / scaleFactor);
        graphicInfo.setHeight(graphicInfo.getHeight() / scaleFactor);
    }

    protected GraphicInfo calculateDiagramSize(BpmnModel bpmnModel) {
        GraphicInfo diagramInfo = new GraphicInfo();

        for (Pool pool : bpmnModel.getPools()) {
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            double elementMaxX = graphicInfo.getX() + graphicInfo.getWidth();
            double elementMaxY = graphicInfo.getY() + graphicInfo.getHeight();

            if (elementMaxX > diagramInfo.getWidth()) {
                diagramInfo.setWidth(elementMaxX);
            }
            if (elementMaxY > diagramInfo.getHeight()) {
                diagramInfo.setHeight(elementMaxY);
            }
        }

        for (Process process : bpmnModel.getProcesses()) {
            calculateWidthForFlowElements(process.getFlowElements(), bpmnModel, diagramInfo);
            calculateWidthForArtifacts(process.getArtifacts(), bpmnModel, diagramInfo);
        }
        return diagramInfo;
    }

    protected void calculateWidthForFlowElements(Collection<FlowElement> elementList, BpmnModel bpmnModel, GraphicInfo diagramInfo) {
        for (FlowElement flowElement : elementList) {
            List<GraphicInfo> graphicInfoList = new ArrayList<>();
            if (flowElement instanceof SequenceFlow) {
                List<GraphicInfo> flowGraphics = bpmnModel.getFlowLocationGraphicInfo(flowElement.getId());
                if (flowGraphics != null && flowGraphics.size() > 0) {
                    graphicInfoList.addAll(flowGraphics);
                }
            } else {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowElement.getId());
                if (graphicInfo != null) {
                    graphicInfoList.add(graphicInfo);
                }
            }

            processGraphicInfoList(graphicInfoList, diagramInfo);
        }
    }

    protected void calculateWidthForArtifacts(Collection<Artifact> artifactList, BpmnModel bpmnModel, GraphicInfo diagramInfo) {
        for (Artifact artifact : artifactList) {
            List<GraphicInfo> graphicInfoList = new ArrayList<>();
            if (artifact instanceof Association) {
                graphicInfoList.addAll(bpmnModel.getFlowLocationGraphicInfo(artifact.getId()));
            } else {
                graphicInfoList.add(bpmnModel.getGraphicInfo(artifact.getId()));
            }

            processGraphicInfoList(graphicInfoList, diagramInfo);
        }
    }

    protected void processGraphicInfoList(List<GraphicInfo> graphicInfoList, GraphicInfo diagramInfo) {
        for (GraphicInfo graphicInfo : graphicInfoList) {
            double elementMaxX = graphicInfo.getX() + graphicInfo.getWidth();
            double elementMaxY = graphicInfo.getY() + graphicInfo.getHeight();

            if (elementMaxX > diagramInfo.getWidth()) {
                diagramInfo.setWidth(elementMaxX);
            }
            if (elementMaxY > diagramInfo.getHeight()) {
                diagramInfo.setHeight(elementMaxY);
            }
        }
    }
}
