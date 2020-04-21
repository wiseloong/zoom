package com.zoom.meta.model;

import com.zoom.tools.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseMeta extends BaseEntity {
    private Long classId;       //规格id
    private String classCode;   //规格编码
}
