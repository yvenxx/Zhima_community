package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.model.Article;
import cn.yvenxx.zhima_community.model.Comment;
import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.service.ArticleService;
import cn.yvenxx.zhima_community.service.CommentService;
import cn.yvenxx.zhima_community.service.UserService;
import cn.yvenxx.zhima_community.utils.R;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @GetMapping("users/{currentPage}")
    public R getUsers(@PathVariable("currentPage")int currentPage){
        PageInfo<User> allUser = userService.getAllUser(currentPage);
        return R.succ(allUser);
    }

    @DeleteMapping("/user/{id}")
    public R deleteUser(@PathVariable("id")int id){
        int code = userService.deleteUser(id);
        if (code==1){
            return R.succ("删除成功",null);
        }
        return R.fail("删除失败");
    }

    @PutMapping("/user/{id}")
    public R updateUser(@PathVariable("id")int id,@RequestBody User user) {
        user.setId(id);
        if(userService.update(user)){
            return R.succ(null);
        }
        return R.fail("修改失败");
    }


    @DeleteMapping("article/{id}")
    public R deleteArticle(@PathVariable("id")int articleId){
        int i = articleService.adminDeleteArticle(articleId);
        if (i==1){
            return R.succ(null);
        }
        return R.fail("删除失败");
    }

    @GetMapping("articles/{currentPage}")
    public R getArticles(@PathVariable("currentPage")int currentPage){
        PageInfo<Article> list = articleService.getAllArticle(currentPage);
        return R.succ(list);
    }

    @GetMapping("comments/{currentPage}")
    public R getComments(@PathVariable("currentPage") int currentPage){
        PageInfo<Comment> list = commentService.getAllComments(currentPage);
        return R.succ(list);
    }

    /**
     * 删除的时候，要将他下面的子评论，或者说回复他的评论删除
     * @param id
     * @return
     */
    @DeleteMapping("comment/{id}")
    public R deleteComment(@PathVariable("id")int id){
        if(commentService.deleteComment(id)>=1){
            return R.succ(null);
        }
        return R.fail("删除失败");
    }
}
