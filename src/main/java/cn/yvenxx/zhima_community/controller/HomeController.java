package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.controller.utils.R;
import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.service.impl.ArticleServiceImpl;
import cn.yvenxx.zhima_community.service.impl.UserServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class HomeController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ArticleServiceImpl articleService;

    @ResponseBody
    @RequestMapping({"/","/index"})
    public R index(@RequestParam("token") String token){
        log.info(token);
        User user = userService.findUserByToken(token);
        if (user!=null){
            return R.succ(user);
        }
        return R.fail("未登录");
    }

    @ResponseBody
    @RequestMapping("/index/articles/{currentPage}")
    public R articlePage(@PathVariable("currentPage") int currentPage){
        PageInfo<Article> allArticle = articleService.getPages(currentPage);
        log.info(allArticle.toString());
        return R.succ(allArticle);
    }

    @RequestMapping("/login")
    public String loginPage(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(username!=null){
            return "/index";
        }
        return "login";
    }
}
