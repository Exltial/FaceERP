package com.ncut.face.erp.service.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final String SHORT = "yyyy-MM-dd";
    public static final String DATE = "yyyy年MM-dd";

    public static String format(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
}
