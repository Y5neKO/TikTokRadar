package com.y5neko.tiktokradar.misc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    public static String convertTimestampToDate(long timestamp) {
        // 创建SimpleDateFormat对象，设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        // 如果时间戳是秒级的，需要将其转换为毫秒级
        if (String.valueOf(timestamp).length() == 10) {
            timestamp *= 1000;
        }

        // 创建Date对象并格式化
        Date date = new Date(timestamp);
        return sdf.format(date);
    }
}
