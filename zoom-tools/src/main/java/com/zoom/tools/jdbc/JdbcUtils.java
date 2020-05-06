package com.zoom.tools.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Component
public class JdbcUtils {

    private static JdbcTemplate jdbcTemplate;
    private static JdbcUtils jdbcUtils;

    public JdbcUtils(JdbcTemplate jdbcTemplate) {
        JdbcUtils.jdbcTemplate = jdbcTemplate;
    }

    public static List<Map<String, Object>> query(@NonNull final String tableName,
                                                  final List<String> cols,
                                                  final Map<String, Object> whereMap) {
        return null;
    }

    public static Map<String, Object> getById(@NonNull final String tableName,
                                              @NonNull final Long pkValue,
                                              final String pkName) {
        Map<String, Object> a = jdbcTemplate.queryForMap("select  * from cm_person where id =1");
        return null;
    }

    public static Map<String, Object> abc() {
        Map<String, Object> a = jdbcTemplate.queryForMap("select  * from cm_person where id =1");
        return null;
    }

    public static Map<String, Object> getById(@NonNull final String tableName, @NonNull final Long id) {
        Map<String, Object> a = jdbcTemplate.queryForMap("select  * from sm_role where id =1");
        System.out.println(a);
        return null;
    }

    /**
     * 用于多数据源的使用
     *
     * @param dataSource 使用AbstractRoutingDataSource
     */
    public static synchronized JdbcUtils getInstance(DataSource dataSource) {
        if (jdbcUtils == null) {
            jdbcUtils = new JdbcUtils(new JdbcTemplate(dataSource));
        }
        return jdbcUtils;
    }
}
