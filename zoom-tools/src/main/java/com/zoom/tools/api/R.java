package com.zoom.tools.api;

import lombok.Data;

import java.util.List;

/**
 * 统一返回类型
 */
@Data
public class R<T> {
    /**
     * 状态
     */
    private Integer code;
    /**
     * 结果
     */
    private T data;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 错误信息集合，适合导入，导出这种，可以列表展示所有的错误信息
     */
    private List<String> msgList;

    public static <T> R<T> ok(T data) {
        R<T> apiResult = new R<>();
        apiResult.setCode(200);
        apiResult.setData(data);
        apiResult.setMsg(null);
        return apiResult;
    }

    public static <T> R<T> failed(String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(400);
        apiResult.setData(null);
        apiResult.setMsg(msg);
        return apiResult;
    }

}
