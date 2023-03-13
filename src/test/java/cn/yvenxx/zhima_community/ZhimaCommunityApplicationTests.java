package cn.yvenxx.zhima_community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class ZhimaCommunityApplicationTests {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("hello","world");
        String s = ops.get("hello");
        System.out.println(s);
    }

}
