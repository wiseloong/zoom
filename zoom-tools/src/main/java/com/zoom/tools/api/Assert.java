package com.zoom.tools.api;

import org.springframework.lang.Nullable;

/**
 * 断言处理类，用于抛出各种API异常
 */
public abstract class Assert {

    public static void state(boolean expression, String message) {
        if (expression) {
            throw new ApiException(message);
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new ApiException(message);
        }
    }

    public static void notNull(@Nullable Object object) {
        notNull(object, "有数据为空！");
    }

}
