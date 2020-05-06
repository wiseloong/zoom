package com.zoom.tools.jdbc;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    private static final List<Object> dataSourceKeys = new ArrayList<>();

    public static void set(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String get() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }

    public static boolean contains(String dataSourceId) {
        return dataSourceKeys.contains(dataSourceId);
    }

    public static DynamicDataSource dynamicDataSource(@NonNull Map<Object, Object> targetDataSources) {
        return dynamicDataSource(targetDataSources, "primary");
    }

    public static DynamicDataSource dynamicDataSource(@NonNull Map<Object, Object> targetDataSources, @NonNull String primaryKey) {
        dataSourceKeys.addAll(targetDataSources.keySet());
        Object defaultTargetDataSource = targetDataSources.get(primaryKey);
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(defaultTargetDataSource);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        JdbcUtils.getInstance(dynamicDataSource);
        return dynamicDataSource;
    }
}
