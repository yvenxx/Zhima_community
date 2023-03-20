package cn.yvenxx.zhima_community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

@SpringBootTest
class ZhimaCommunityApplicationTests {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        long zero = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.println(zero);
    }

}
