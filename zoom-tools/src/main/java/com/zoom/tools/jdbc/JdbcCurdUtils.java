package com.zoom.tools.jdbc;

import com.zoom.tools.api.Assert;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class JdbcCurdUtils {

    private static JdbcTemplate jdbcTemplate;

    private static JdbcCurdUtils jdbcCurdUtils;

    public JdbcCurdUtils(JdbcTemplate jdbcTemplate) {
        JdbcCurdUtils.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_ALL_SQL = "";
    private static final String SELECT_COUNT_SQL = "select count(1) ";

    public static List<Map<String, Object>> query(@NonNull final String tableName,
                                                  final List<String> cols,
                                                  final Map<String, Object> whereMap) {
        return null;
    }

    public static Map<String, Object> findById(@NonNull final String tableName,
                                               @NonNull final Long pkValue,
                                               final String pkName) {
        Assert.notNull(pkValue, "查询主键值不能为空！");
        Assert.notNull(pkName, "查询主键名称不能为空！");
        var sql = "select * from " + tableName + " where " + pkName + " = " + pkValue;
        return jdbcTemplate.queryForMap(sql);
    }

    public static Map<String, Object> findById(@NonNull final String tableName, @NonNull final Long id) {
        return findById(tableName, id, "id");
    }

    public static Map<String, Object> count(@NonNull final String tableName, @NonNull final Long id) {
        return findById(tableName, id, "id");
    }

    public static long insert(@NonNull final String tableName,
                              @NonNull final Map<String, Object> data) {
        var ks = new ArrayList<String>();
        var vs = new ArrayList<String>();
        var vals = new ArrayList<>();
        data.forEach((key, value) -> {
            ks.add(key);
            vs.add("?");
            vals.add(value);
        });
        var kSql = String.join(", ", ks);
        var vSql = String.join(", ", vs);
        var sql = "insert into " + tableName + " (" + kSql + ") values (" + vSql + ")";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(getPreparedStatementCreator(sql, vals, true), keyHolder);
        Number key = keyHolder.getKey();
        return (key == null) ? 1L : key.longValue();
    }

    public static int updateById(@NonNull final String tableName,
                                 @NonNull final Map<String, Object> data) {
        var newData = data.entrySet().stream()
                .filter(map -> !"id".equals(map.getKey().toLowerCase()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Object id = data.get("id");
        if (id == null) {
            id = data.get("ID");
        }
        return updateById(tableName, newData, (Long) id);
    }

    public static int updateById(@NonNull final String tableName,
                                 @NonNull final Map<String, Object> data,
                                 @NonNull final Long id) {
        Assert.notNull(data, "更新数据不能为空！");
        Assert.notNull(id, "更新主键值不能为空！");
        var kvs = new ArrayList<String>();
        var vals = new ArrayList<>();
        data.forEach((key, value) -> {
            String vSql;
            if (value == null) {
                vSql = " = null";
            } else {
                vSql = " = ?";
                vals.add(value);
            }
            kvs.add(key + vSql);
        });
        var kvSql = String.join(", ", kvs);
        var sql = "update " + tableName + " set " + kvSql + " where id = ?";
        vals.add(id);
        log.debug("更新语句为:[{}];参数为:{}", sql, vals);
        return jdbcTemplate.update(sql, vals.toArray());
    }

    public static int deleteById(@NonNull final String tableName,
                                 @NonNull final Long id) {
        var sql = "delete from " + tableName + " where id = ?";
        log.debug("删除语句为:[{}];参数为:[{}]", sql, id);
        return jdbcTemplate.update(sql, id);
    }

    public static int deleteByIds(@NonNull final String tableName,
                                  @NonNull final List<Long> ids) {
        ids.removeAll(Collections.singleton(null));
        var ins = Collections.nCopies(ids.size(), "?");
        var inSql = String.join(", ", ins);
        var sql = "delete from " + tableName + " where id in (" + inSql + ")";
        log.debug("删除语句为:[{}];参数为:{}", sql, ids);
        return jdbcTemplate.update(sql, ids.toArray());
    }

    protected static PreparedStatementCreator getPreparedStatementCreator(String sql, List<?> params, boolean generatedKeys) {
        List<Integer> types = Collections.nCopies(params.size(), JdbcUtils.TYPE_UNKNOWN);
        int[] ints = types.stream().mapToInt(Integer::valueOf).toArray();
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(sql, ints);
        if (generatedKeys) {
            pscf.setReturnGeneratedKeys(true);
        }
        return pscf.newPreparedStatementCreator(params);
    }

    /**
     * 用于多数据源的使用
     *
     * @param dataSource 使用AbstractRoutingDataSource
     */
    public static synchronized JdbcCurdUtils getInstance(DataSource dataSource) {
        if (jdbcCurdUtils == null) {
            jdbcCurdUtils = new JdbcCurdUtils(new JdbcTemplate(dataSource));
        }
        return jdbcCurdUtils;
    }
}
