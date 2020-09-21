package com.zoom.tools.api;

import com.zoom.tools.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义异常处理
 * todo 需要加入日志管理
 */
@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Map<String, Object>> handle(ApiException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
//        String s=  Arrays.stream(e.getStackTrace()).filter(o-> o.getClassName().startsWith("s")).findAny().map(StackTraceElement::toString).orElse(null);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
