package com.zoom.tools.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 统一日志打印接口调用信息
 */
@Slf4j
//@Aspect
@Component
public class WebLogAspect {

    private final ObjectMapper mapper;

    public WebLogAspect(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 换行符
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * com.zoom.tools.controller..*.*(..))");
        DefaultPointcutAdvisor pa = new DefaultPointcutAdvisor();
        pa.setPointcut(pointcut);
        pa.setAdvice((MethodInterceptor) methodInvocation -> {
            logRequest(logClass(methodInvocation), logDescription(methodInvocation), methodInvocation.getArguments());
            long startTime = System.currentTimeMillis();
            Object result = methodInvocation.proceed();
            logResponse(startTime, result);
            return result;
        });
        return pa;
    }

    /**
     * 以 RestController 包下定义的所有请求为切入点
     */
    @Pointcut("execution(public * com.zoom.tools.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        String clazz = logClass(joinPoint);
        String description = logDescription(joinPoint);
        Object[] args = joinPoint.getArgs();
        logRequest(clazz, description, args);
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        logResponse(startTime, result);
        return result;
    }

    private String logClass(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        return className + "." + methodName;
    }

    private String logClass(MethodInvocation methodInvocation) {
        String className = methodInvocation.getClass().getName();
        String methodName = methodInvocation.getMethod().getName();
        return className + "." + methodName;
    }

    private String logDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        ApiOperation annotation;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                annotation = method.getAnnotation(ApiOperation.class);
                if (annotation != null) {
                    description = annotation.value();
                }
                break;
            }
        }
        return description;
    }

    private String logDescription(MethodInvocation methodInvocation) {
        Method method = methodInvocation.getMethod();
        String description = "";
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        if (annotation != null) {
            description = annotation.value();
        }
        return description;
    }

    private void logRequest(String clazz, String description, Object[] args) throws JsonProcessingException {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        // 打印请求相关参数
        log.info("========================================== Start ==========================================");
        // 打印请求 url
        log.info("URL            : {}", request.getRequestURL().toString());
        // 打印描述信息
        log.info("Description    : {}", description);
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}", clazz);
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteAddr());
        // 打印请求入参
        log.info("Request Args   : {}", mapper.writeValueAsString(args));
    }

    private void logResponse(long startTime, Object result) throws JsonProcessingException {
        // 开始打印请求日志
        // 打印出参
        log.info("Response Args  : {}", mapper.writeValueAsString(result));
        // 执行耗时
        log.info("Time Consuming : {} ms", System.currentTimeMillis() - startTime);
        log.info("=========================================== End ==========================================={}", LINE_SEPARATOR);
    }
}
