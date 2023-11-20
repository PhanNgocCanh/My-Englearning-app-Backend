package com.exerciseapp.myapp.utils;

public class StringUtil {

    private static final String EMPTY_CHARACTER = "";

    public static String escapePath(String path) {
        if (path == null || EMPTY_CHARACTER.equals(path)) {
            return "";
        }
        String s = path.replace("\\", "\\\\");
        s = s.replace("%", "\\%");
        s = s.replace("_", "\\_");

        return s;
    }

    public static String validateKeywordSearchNull(String value) {
        if (value != null && !value.isEmpty()) {
            return escapePath(value).toLowerCase();
        }

        return null;
    }
}
