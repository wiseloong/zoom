package com.zoom.security.model;

import com.zoom.tools.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person extends BaseEntity {
    private static final long serialVersionUID = -6409251271057743027L;
    private String username;
    private String password;
}
