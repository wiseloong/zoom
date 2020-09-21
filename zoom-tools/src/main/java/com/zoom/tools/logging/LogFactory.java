package com.zoom.tools.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装Log日志工厂类
 */
public class LogFactory {

    /**
     * 模仿Slf4j，使用：private static final Log LOG = LogFactory.getLogger(LogFactory.class);
     */
    public static Log getLogger(Class<?> aClass) {
        return new Log(LoggerFactory.getLogger(aClass));
    }

    public static Log getLogger(String logger) {
        return new Log(LoggerFactory.getLogger(logger));
    }

    /**
     * 可以结合 @Slf4j 一起使用，
     * 例如：private static final Log LOG = LogFactory.getLogger(log);
     * 即可以使用Slf4j的log，也可以使用封装的LOG
     * log.info("====== log示例 ======");
     * LOG.debug(() -> "====== LOG示例 ======");
     */
    public static Log getLogger(Logger logger) {
        return new Log(logger);
    }

}
