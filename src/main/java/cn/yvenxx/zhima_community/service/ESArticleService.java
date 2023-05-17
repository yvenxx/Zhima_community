package cn.yvenxx.zhima_community.service;

import cn.yvenxx.zhima_community.model.ESArticle;
import com.github.pagehelper.PageInfo;

public interface ESArticleService {

    PageInfo<ESArticle> searchByTitle(int currentPage,String title);

    void updateArticleToES();

}
