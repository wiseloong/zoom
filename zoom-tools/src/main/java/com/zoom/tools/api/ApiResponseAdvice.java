package com.zoom.tools.api;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {
    //过滤包路径，这里可以配置成yml资源
    private static final List<String> adviceFilterPackage =
            Arrays.asList("org.springframework", "springfox.documentation");
//    private final List<String> adviceFilterClass; //类过滤列表

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return filter(methodParameter);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o == null) {
            return R.ok(null);
        }
        if (o instanceof ResponseEntity){
            return o;
        }
        if (o instanceof R) {
            return o;
        }
        return R.ok(o);
    }

    private Boolean filter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getDeclaringClass();
        // 检查过滤包路径
        Optional<String> any = adviceFilterPackage.stream()
                .filter(o -> clazz.getName().contains(o)).findAny();
        if (any.isPresent()) {
            return false;
        }
        // 检查<类>过滤列表
//        if (adviceFilterClass.contains(clazz.getName())) {
//            return false;
//        }
        // 检查注解是否存在
//        if (clazz.isAnnotationPresent(IgnoreResponseAdvice.class)) {
//            return false;
//        }
//        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
//            return false;
//        }
        return true;
    }
}
