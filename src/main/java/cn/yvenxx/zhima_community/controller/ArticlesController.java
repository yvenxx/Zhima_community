package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.utils.R;
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

    @RequestMapping("/article/{id}")
    public R articleDetail(@PathVariable("id")int id){
        Article articleDetail = articleService.getArticleDetail(id);
        return R.succ(articleDetail);
    }

    @RequestMapping("/index/{category}/{sort}/{currentPage}")
    public R mixLatestArticlePage(@PathVariable("category")String category,
                                  @PathVariable("sort")String sort,
                                  @PathVariable("currentPage") int currentPage){
        /*
          获得综合栏目的最新文章
         */
        if (sort.equals("latest")){
            PageInfo<Article> allArticle = articleService.getLatestArticlesByCategory(category,currentPage);
            return R.succ(allArticle);
        }
        else if (sort.equals("recommend")){
            PageInfo<Article> allArticle = articleService.getRecommendArticlesByCategory(category,currentPage);
            return R.succ(allArticle);
        }
        return R.fail("fail");
    }

    @RequestMapping("/index/hotArticles")
    public R mixHotArticles(){
        return R.succ(articleService.getMixHotArticles());
    }

}
