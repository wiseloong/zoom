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
    protected Long tenantId;  //租户id

    protected Long creatorId;
    protected Date createDate;
    protected Long modifierId;
    protected Date modifyDate;

    public void create(Long userId) {
        Date date = new Date();
        this.creatorId = userId;
        this.modifierId = userId;
        if (createDate == null) {
            this.createDate = date;
        }
        if (modifyDate == null) {
            this.modifyDate = date;
        }
        if (delete == null) {
            this.delete = 0;
        }
        if (version == null) {
            this.version = 1;
        }
    }

    public void update(Long userId) {
        this.creatorId = null;
        this.createDate = null;
        this.modifierId = userId;
        this.modifyDate = new Date();
        if (version != null) {
            this.version++;
        }
    }
}
