package cn.yvenxx.zhima_community.mapper;

import cn.yvenxx.zhima_community.model.Article;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    int insert(Article article);

    Page<Article> getAllArticle();
}
