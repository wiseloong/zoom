package com.zoom.security.annotation;

import java.lang.annotation.*;

/**
 * 标注此注解的controller不需要登陆
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLogin {
}
