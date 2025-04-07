package com.tvd.jsonstorge.constant;

import java.time.format.DateTimeFormatter;

public class DateTimeConstant {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
}
