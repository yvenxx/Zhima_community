package cn.yvenxx.zhima_community.service.impl;

import cn.yvenxx.zhima_community.mapper.ArticleMapper;
import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.model.ESArticle;
import cn.yvenxx.zhima_community.repository.ESArticleRepository;
import cn.yvenxx.zhima_community.service.ESArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ESArticleServiceImpl implements ESArticleService {

    @Autowired
    ESArticleRepository esArticleRepository;

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public PageInfo<ESArticle> searchByTitle(int currentPage,String title) {
        PageHelper.startPage(currentPage,5);
        PageInfo<ESArticle> list = new PageInfo<>(esArticleRepository.queryESArticleByTitle(title));
        return list;
    }

    @Override
    public void updateArticleToES() {
        List<Article> articles = articleMapper.getAllArticles();
        for (Article article : articles) {
            esArticleRepository.save(new ESArticle(article.getAid(),article.getTitle()));
        }
    }
}
