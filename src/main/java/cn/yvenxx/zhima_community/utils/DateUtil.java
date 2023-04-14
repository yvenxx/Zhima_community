package cn.yvenxx.zhima_community.utils;

import java.time.*;

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

    /**
     * 将timestamp转为localDateTime
     * @param timestamp
     * @return
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }
}
