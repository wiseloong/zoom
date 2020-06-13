package com.zoom.tools.mybatis;

import com.zoom.tools.util.StrUtil;

public class SqlUtil {

    private static final String AND = " AND ";
    private static final String OR = " OR ";

    public static String as(String as) {
        if (as == null) {
            return "";
        } else {
            return as + ".";
        }
    }

    public static String enabled(String as) {
        String a = SqlUtil.as(as);
        String sql = a + "is_valid = 1 and IFNULL(" + a + "is_delete, 0) != 1";
        return space(sql);
    }

    public static String andEnabled(String as) {
        return AND + enabled(as);
    }

    public static String space(String sql) {
        return " " + sql + " ";
    }

    public static String script(String sql) {
        return "<script>" + sql + "</script>";
    }

    public static String eq(String column) {
        return eq(null, column, null);
    }

    public static String eq(String as, String column) {
        return eq(as, column, null);
    }

    /**
     * @param as     是否有as名称，没有给null。
     * @param column 数据库字段名称
     * @param entity 对象名称，当是单一字段时，给null，例如：@Param("e") Entity entity，则需要给 e
     * @return 数据库语句等于，例如：name = #{name}
     */
    public static String eq(String as, String column, String entity) {
        String a = SqlUtil.as(as);
        String property = StrUtil.toCamelCase(column);
        if (entity != null) {
            property = entity + "." + property;
        }
        String sql = a + column + " = #{" + property + "}";
        return space(sql);
    }

    public static String andEq(String as, String column) {
        return AND + eq(as, column);
    }

    public static String in(String as, String column) {
        String a = SqlUtil.as(as);
        String property = StrUtil.toCamelCase(column) + "s";
        String sql = a + column + " in <foreach item='item' collection='" + property
                + "' open='(' separator=',' close=')'>#{item.id}</foreach>";
        return space(sql);
    }

    public static String andIn(String as, String column) {
        return AND + in(as, column);
    }

    public static String like(String as, String column, String value) {
        String a = SqlUtil.as(as);
        String sql = a + column + " like '%" + value + "%'";
        return space(sql);
    }

    public static String andLike(String as, String column, String value) {
        return AND + like(as, column, value);
    }
}
