package com.cav.clairvoyance.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UUIDUtil {
    private static String pattern = "YYYYMMddHHmmss";

    // 产生后缀是时间的UUID
    public static String getUUIDEndWithTime() {

        return UUID.randomUUID().toString().replace("-", "") + new SimpleDateFormat(pattern).format(new Date());
    }


    /**
     * 从UUID中获取时间 YYYY-MM-dd
     *
     * @param uuid
     * @return
     */
    public static String getTimeFromUUID(String uuid) {
        String time = uuid.substring(uuid.length() - pattern.length());
        String year = time.substring(0, 4);
        String month = time.substring(4, 6);
        String day = time.substring(6, 8);
        return year + "-" + month + "-" + day;
    }
}
