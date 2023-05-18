package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.service.ArticleService;
import cn.yvenxx.zhima_community.service.UserService;
import cn.yvenxx.zhima_community.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonalController {
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;

    @DeleteMapping("/user/{uid}/article/{aid}")
    public R deleteArticleById(@PathVariable("uid")int uid,@PathVariable("aid") int aid){

        int i = articleService.deleteArticle(uid,aid);
        if (i==1){
            return R.succ(null);
        }
        return R.fail("删除失败");
    }
}
