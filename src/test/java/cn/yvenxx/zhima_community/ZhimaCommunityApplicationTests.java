package cn.yvenxx.zhima_community;

import cn.yvenxx.zhima_community.mapper.ArticleMapper;
import cn.yvenxx.zhima_community.model.ESArticle;
import cn.yvenxx.zhima_community.repository.ESArticleRepository;
import cn.yvenxx.zhima_community.service.ESArticleService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class ZhimaCommunityApplicationTests {

//    @Autowired
//    StringRedisTemplate redisTemplate;

    @Autowired
    ESArticleRepository esArticleRepository;

    @Autowired
    ESArticleService esArticleService;

    @Autowired
    ArticleMapper articleMapper;
    @Test
    void contextLoads() {
        long zero = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.println(zero);
    }

    @Test
    void EsTest(){
        List<ESArticle> list = esArticleRepository.queryESArticleByTitle("标题");
        System.out.println("====================="+list);
    }

    @Test
    void ArticleTest(){
        esArticleService.updateArticleToES();
    }

}
