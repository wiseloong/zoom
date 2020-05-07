package com.zoom.security.model;

import com.zoom.tools.model.BaseEntity;

public class Permission extends BaseEntity {
    private static final long serialVersionUID = -6153250278956314533L;
    private String icon;
    private String url;
    private String path;
    private Integer sort;
    private Long typeId;
    private Long parentId;
}
