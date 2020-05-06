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
public class BaseEntity implements Serializable {

    private Long id;
    private String code;
    private String name;
    private String notes;
    private Integer version;
    private Integer delete; //是否删除
    private Integer valid;  //是否启用
    private Long tenantId;  //租户id

    private Long creatorId;
    private Date createDate;
    private Long modifierId;
    private Date modifyDate;

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
