package cn.yvenxx.zhima_community.service.impl;

import cn.yvenxx.zhima_community.mapper.ArticleMapper;
import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public void publishAtricle(Article article) {
        articleMapper.insert(article);
    }
}
