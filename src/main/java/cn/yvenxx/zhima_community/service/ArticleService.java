package cn.yvenxx.zhima_community.service;

import cn.yvenxx.zhima_community.model.Article;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ArticleService {

    public void publishAtricle(Article article);

    PageInfo<Article> getPages(int currentPage);
}
