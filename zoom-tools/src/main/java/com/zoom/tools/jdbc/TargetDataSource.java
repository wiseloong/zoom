package com.zoom.tools.jdbc;

import java.lang.annotation.*;

/**
 * 标注此注解，用于切换数据源
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {
    String value();
}
