package com.zoom.tools.logging;

import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * 封装日志类，结合Lambda，最大提升debug的性能
 */
public class Log {
    private final Logger log;

    Log(Logger log) {
        this.log = log;
    }

    /**
     * 在info模式下，可以实现即不打印日志，也不计算日志变量
     * 例如：LOG.debug(() -> "debug示例");
     * LOG.debug(() -> "debug示例，i=" + (1 + 2));
     *
     * @param s Lambda表达式，返回日志的内容
     */
    public void debug(Supplier<String> s) {
        if (log.isDebugEnabled()) {
            log.debug(s.get());
        }
    }

    /**
     * 在info模式下，可以实现即不打印日志，也不计算日志变量，同时支持日志里{}引用变量
     * 例如：
     * LOG.debug(() -> "debug示例，i1={}，i2={}",
     * () -> LOG.objects(1, 1+2));
     *
     * @param s Lambda表达式，返回日志的内容
     * @param o Lambda表达式，返回日志的变量集合
     */
    public void debug(Supplier<String> s, Supplier<Object[]> o) {
        if (log.isDebugEnabled()) {
            log.debug(s.get(), o.get());
        }
    }

    /**
     * 结合上面的debug方法使用，返回日志里使用的变量集合
     *
     * @param arguments 日志变量
     */
    public Object[] objects(Object... arguments) {
        return arguments;
    }

    public void info(String msg) {
        log.info(msg);
    }

    public void info(String format, Object... arguments) {
        log.info(format, arguments);
    }

    public void warn(String msg) {
        log.error(msg);
    }

    public void warn(String format, Object... arguments) {
        log.error(format, arguments);
    }

    public void error(String msg) {
        log.error(msg);
    }

    public void error(String format, Object... arguments) {
        log.error(format, arguments);
    }

    public void error(String msg, Throwable t) {
        log.error(msg, t);
    }

    public void trace(Supplier<String> s) {
        if (log.isTraceEnabled()) {
            log.trace(s.get());
        }
    }
}
