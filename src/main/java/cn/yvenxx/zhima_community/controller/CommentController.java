package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.controller.utils.R;
import cn.yvenxx.zhima_community.model.Comment;
import cn.yvenxx.zhima_community.service.CommentService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/comment/{blogid}/{currentPage}")
    public R getComment(@PathVariable("blogid")int blogId,@PathVariable("currentPage") int currentPage){
        PageInfo<Comment> comment = commentService.getCommentByBlogId(currentPage, blogId);
        List<Comment> list = comment.getList();
        List<Comment> rootComment = new ArrayList<Comment>();
        //遍历，将一级评论放到rootComment
        for (Comment comment1 : list) {
            if (comment1.getParentId()==null){
                rootComment.add(comment1);
            }
        }

        //把不是一级节点的评论，放到对应一级节点的children中
        for (Comment comment1 : rootComment) {
            comment1.setChildren(list.stream()
                    .filter(c->
                            comment1.getId().equals(c.getParentId()))
                    .collect(Collectors.toList()));
        }

        //设置total
        int count = commentService.getCountByBlogId(blogId);
        comment.setTotal(count);
        log.info(count+"----------------");
        //设置pages
        count = count/20;
        count=count+count%20==0?0:1;
        comment.setPages(count);
        log.info(count+"----------------");

        comment.setList(rootComment);
        return R.succ(comment);
    }

    @PostMapping("/comment")
    public R comment(Comment comment){
        comment.setCreateTime(LocalDateTime.now());
        int i = commentService.comment(comment);
        if (i==1){
            return R.succ(null);
        }
        return R.fail("插入失败");
    }
}
