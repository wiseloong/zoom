package com.zoom.tools.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(-10) // 保证该AOP在@Transactional之前执行，待验证
@Component
public class DynamicDataSourceAspect {

    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint point, TargetDataSource targetDataSource) throws Throwable {
        String dsId = targetDataSource.value();
        if (!DynamicDataSourceContextHolder.contains(dsId)) {
            log.debug("数据源[{}]不存在，使用默认数据源 > {}", targetDataSource.value(), point.getSignature());
        } else {
            log.debug("UseDataSource : {} > {}", targetDataSource.value(), point.getSignature());
            DynamicDataSourceContextHolder.set(targetDataSource.value());
        }
    }

    @After("@annotation(targetDataSource)")
    public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        log.debug("RevertDataSource : {} > {}", targetDataSource.value(), point.getSignature());
        DynamicDataSourceContextHolder.clear();
    }
}
