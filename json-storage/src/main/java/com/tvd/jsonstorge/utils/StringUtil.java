package com.tvd.jsonstorge.utils;

public class StringUtil {
    public static boolean isNullOrEmpty(String str) {
        return !DataUtil.isNullOrEmpty(str) && str.trim().isEmpty();
    }


}
