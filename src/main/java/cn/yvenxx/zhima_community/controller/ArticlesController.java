package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.model.ESArticle;
import cn.yvenxx.zhima_community.repository.ESArticleRepository;
import cn.yvenxx.zhima_community.service.LikeRedisService;
import cn.yvenxx.zhima_community.service.impl.ArticleServiceImpl;
import cn.yvenxx.zhima_community.utils.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ArticlesController {
    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    LikeRedisService redisService;

    @Autowired
    ESArticleRepository repository;

    @RequestMapping("/article/{id}")
    public R articleDetail(@PathVariable("id")int id){
        Article articleDetail = articleService.getArticleDetail(id);
        //缓存like to redis
        redisService.insertLikeCount(articleDetail.getAid(),articleDetail.getLikeCount());
        return R.succ(articleDetail);
    }

    @RequestMapping("/index/{category}/{sort}/{currentPage}")
    public R latestArticlePage(@PathVariable("category")String category,
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
    public R hotArticles(){
        return R.succ(articleService.getMixHotArticles());
    }

    @GetMapping("/_search")
    public R searchArticle(String title){
        List<ESArticle> list = repository.queryESArticleByTitle(title);
        if (!list.isEmpty()){
            return R.succ(list);
        }
        return R.fail("error");
    }

    @GetMapping("/user/{id}/articles/{page}")
    public R getArticlesByUserId(@PathVariable("id") int id,@PathVariable("page")int currentPage){
        PageInfo<Article> articleByUserId = articleService.getArticleByUserId(currentPage, id);
        return R.succ(articleByUserId);
    }

}
