package com.zoom.flow.util;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.image.exception.FlowableImageException;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageGenerator {

    public static BufferedImage createImage(BpmnModel bpmnModel, double scaleFactor) {
        ProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator(scaleFactor);
        return diagramGenerator.generatePngImage(bpmnModel, scaleFactor);
    }

    public static byte[] createByteArrayForImage(BufferedImage image, String imageType) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, imageType, out);
        } catch (IOException e) {
            throw new FlowableImageException("Error while generating byte array for process image", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
                // Exception is silently ignored
            }
        }
        return out.toByteArray();
    }
}
