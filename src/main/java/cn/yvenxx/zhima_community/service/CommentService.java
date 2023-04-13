package cn.yvenxx.zhima_community.service;

import cn.yvenxx.zhima_community.mapper.CommentMapper;
import cn.yvenxx.zhima_community.model.Comment;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

public interface CommentService {
    PageInfo<Comment> getCommentByBlogId(int currentPage,int blogId);

    int comment(Comment comment);

    int getCountByBlogId(int blogId);
}
