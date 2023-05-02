package cn.yvenxx.zhima_community.service.impl;

import cn.yvenxx.zhima_community.mapper.CommentMapper;
import cn.yvenxx.zhima_community.utils.DateUtil;
import cn.yvenxx.zhima_community.mapper.ArticleMapper;
import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CommentMapper commentMapper;

    @Override
    public void publishAtricle(Article article) {

        articleMapper.insert(article);
    }

    @Override
    public PageInfo<Article> getLatestArticlesByCategory(String category,int currentPage) {
        /*
            获得最新的文章
         */
        PageHelper.startPage(currentPage,5,"gmt_create desc");
        PageInfo<Article> list = new PageInfo<>(articleMapper.getAllByCategoryArticle(category));
        return list;
    }

    @Override
    public PageInfo<Article> getRecommendArticlesByCategory(String category, int currentPage) {
        /*
            获得推荐的文章，暂时没写推荐算法
         */
        PageHelper.startPage(currentPage,5,"gmt_create desc");
        PageInfo<Article> list = new PageInfo<>(articleMapper.getAllByCategoryArticle(category));
        return list;
    }

    @Override
    public Article getArticleDetail(int id) {
        return articleMapper.getArticleByid(id);
    }

    @Override
    public int updateCommentCount(Integer blogId, int count) {
        return articleMapper.updateCount(blogId,count);
    }

    @Override
    public int deleteArticle(int articleId) {
        commentMapper.deleteCommentByBlogId(articleId);
        return articleMapper.deleteArticle(articleId);
    }

    @Override
    public PageInfo<Article> getAllArticle(int currentPage) {
        PageHelper.startPage(currentPage,10,"gmt_create desc");
        PageInfo<Article> list = new PageInfo<>(articleMapper.getAllArticles());
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
