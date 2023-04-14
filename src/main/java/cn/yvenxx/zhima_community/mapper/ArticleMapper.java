package cn.yvenxx.zhima_community.mapper;

import cn.yvenxx.zhima_community.model.Article;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Article> getArticlesByViewCount();

    int insert(Article article);

    Page<Article> getAllByCategoryArticle(String category);

    int getWithinSevenDaysCount(Long milli);

    List<Article> getMixHotArticles();

    List<Article> getWithinSevenDaysHotArticles(long milli);

    Article getArticleByid(int id);

    int update(Article article);
}
