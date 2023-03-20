package cn.yvenxx.zhima_community.controller.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class DateUtil {
    public static long getAssignDaysMilli(int num){
        /**
         * 获得指定天数的，那一天0点的毫秒值
         */
        // 获取当天的毫秒
        long zero = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        long oneDay = 1000L*60*60*24*num;
        return zero-oneDay;
    }
}
