package com.zoom.meta.model;

import com.zoom.tools.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseMeta extends BaseEntity {
    protected Long classId;       //规格id
    protected String classCode;   //规格编码
}
