package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.controller.utils.R;
import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.service.impl.ArticleServiceImpl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ArticlesController {
    @Autowired
    ArticleServiceImpl articleService;


    @RequestMapping("/index/latestArticles/{currentPage}")
    public R mixLatestArticlePage(@PathVariable("currentPage") int currentPage){
        /*
          获得综合栏目的最新文章
         */
        PageInfo<Article> allArticle = articleService.getMixLatestArticles(currentPage);
//        log.info(allArticle.toString());
        return R.succ(allArticle);
    }

    @RequestMapping("/index/hotArticles")
    public R mixHotArticles(){
        return R.succ(articleService.getMixHotArticles());
    }

}
