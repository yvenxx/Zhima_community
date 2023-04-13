package cn.yvenxx.zhima_community.service;

import cn.yvenxx.zhima_community.model.Article;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ArticleService {

    void publishAtricle(Article article);

    PageInfo<Article> getLatestArticlesByCategory(String category,int currentPage);

    List<Article> getMixHotArticles();

    PageInfo<Article> getRecommendArticlesByCategory(String category, int currentPage);

    Article getArticleDetail(int id);
}
