package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.service.impl.ArticleServiceImpl;
import cn.yvenxx.zhima_community.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Slf4j
public class PublishController {

    @Autowired
    ArticleServiceImpl articleService;

    @Autowired
    UserServiceImpl userService;
    @GetMapping("/publish")
    public String publishPage(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Article article,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("title",article.getTitle());
        model.addAttribute("description",article.getDescription());
        model.addAttribute("category",article.getCategory());

        User user=null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userService.findUserByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        article.setCreator(user.getId());
        article.setGmtCreate(System.currentTimeMillis());
        article.setGmtModified(article.getGmtCreate());


        article.setCommentCount(0);
        article.setViewCount(0);
        article.setLikeCount(0);

        articleService.publishAtricle(article);
        return "/index";
    }
}

