package cn.yvenxx.zhima_community.mapper;

import cn.yvenxx.zhima_community.model.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    int insert(Article article);
}
