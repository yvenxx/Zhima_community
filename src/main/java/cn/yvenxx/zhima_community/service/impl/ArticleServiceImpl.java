package cn.yvenxx.zhima_community.service.impl;

import cn.yvenxx.zhima_community.controller.utils.DateUtil;
import cn.yvenxx.zhima_community.mapper.ArticleMapper;
import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.service.ArticleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public void publishAtricle(Article article) {
        articleMapper.insert(article);
    }

    @Override
    public PageInfo<Article> getMixLatestArticles(int currentPage) {
        /*
            获得最新的文章
         */
        PageHelper.startPage(currentPage,5,"gmt_create desc");
        PageInfo<Article> list = new PageInfo<>(articleMapper.getAllArticle());
        return list;
    }

    @Override
    public List<Article> getMixHotArticles() {
        /*
          获取当前热门，如果今日有五条，则用今日的，否则用今日的加上过去的
         */
        long milli = DateUtil.getAssignDaysMilli(7);
        int count = articleMapper.getWithinSevenDaysCount(milli);
        if (count>5){
            return articleMapper.getWithinSevenDaysHotArticles(milli);
        }
        return articleMapper.getMixHotArticles();
    }
}
