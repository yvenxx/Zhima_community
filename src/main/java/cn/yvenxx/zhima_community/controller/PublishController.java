package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.controller.utils.R;
import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.service.impl.ArticleServiceImpl;
import cn.yvenxx.zhima_community.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
public class PublishController {

    @Autowired
    ArticleServiceImpl articleService;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/publish")
    public R doPublish(Article article,
                       @RequestParam("token") String token) {
        log.info(article.toString());
        User user = null;

        user = userService.findUserByToken(token);
        log.info("======token:"+token);
        log.info("======user:"+user);

        if (user == null) {
//            model.addAttribute("error","用户未登录");
            return R.fail("用户未登录");
        }
        article.setCreator(user.getId());
        article.setGmtCreate(System.currentTimeMillis());
        article.setGmtModified(article.getGmtCreate());

        article.setCommentCount(0);
        article.setViewCount(0);
        article.setLikeCount(0);
        articleService.publishAtricle(article);

        return R.succ(null, "发布成功");
    }
}

