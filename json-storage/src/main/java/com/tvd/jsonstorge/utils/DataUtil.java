package com.tvd.jsonstorge.utils;

public class DataUtil {

    public static boolean isNullOrEmpty(Object obj) {
        return obj == null;
    }

    public static boolean isNullOrEmptyArray(Object[] obj) {
        return obj == null && obj.length == 0;
    }
}
