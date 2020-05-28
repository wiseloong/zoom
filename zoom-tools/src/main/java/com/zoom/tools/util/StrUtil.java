package com.zoom.tools.util;

import org.springframework.util.StringUtils;

import java.util.Arrays;

public class StrUtil {

    public static final String UNDERLINE = "_";

    public static String toCamelCase(String str) {
        if (null == str) {
            return null;
        }
        if (str.contains(UNDERLINE)) {
            StringBuilder builder = new StringBuilder();
            Arrays.asList(str.split(UNDERLINE))
                    .forEach(temp -> builder.append(StringUtils.capitalize(temp)));
            return StringUtils.uncapitalize(builder.toString());
        } else {
            return str;
        }
    }

}
