package cn.yvenxx.zhima_community.service.impl;

import cn.yvenxx.zhima_community.mapper.ArticleMapper;
import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.service.ArticleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageInfo<Article> getPages(int currentPage) {
        PageHelper.startPage(currentPage,5,"gmt_create desc");

        PageInfo<Article> list = new PageInfo<>(articleMapper.getAllArticle());

        return list;
    }
}
