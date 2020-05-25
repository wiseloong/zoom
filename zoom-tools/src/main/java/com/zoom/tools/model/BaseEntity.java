package com.zoom.tools.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础数据对象类
 */
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    protected Long id;
    protected String code;
    protected String name;
    protected String notes;
    protected Integer version;
    protected Integer delete; //是否删除
    protected Integer valid;  //是否启用
    protected Long tenantId;  //租户id

    protected Long creatorId;
    protected Date createDate;
    protected Long modifierId;
    protected Date modifyDate;

    public void create(Long userId) {
        this.creatorId = userId;
        this.createDate = new Date();
        this.modifierId = userId;
        this.modifyDate = new Date();
    }

    public void update(Long userId) {
        this.modifierId = userId;
        this.modifyDate = new Date();
    }
}
