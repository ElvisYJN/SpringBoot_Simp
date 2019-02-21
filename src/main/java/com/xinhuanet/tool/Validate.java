package com.xinhuanet.tool;

public class Validate {
    public static boolean isNull(String value) {
        return value == null || "".equals(value.trim()) || "null".equals(value.trim());
    }

    public static boolean noNull(String value) {
        return value != null && !"".equals(value.trim()) && !"null".equals(value.trim());
    }

    public static String isNullTodefault(String value, String defalutValue) {
        return isNull(value) == true ? defalutValue : value;
    }

    public static boolean isZero(String value) {
        return value == "0" || "0".equals(value.trim());
    }

    public static String isZeroTodefault(String value, String defalutValue) {
        return isZero(value) == true ? defalutValue : value;
    }
}

