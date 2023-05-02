package cn.yvenxx.zhima_community.service;

import cn.yvenxx.zhima_community.model.Comment;
import com.github.pagehelper.PageInfo;

public interface CommentService {
    PageInfo<Comment> getCommentByBlogId(int currentPage,int blogId);

    int comment(Comment comment);

    int getCountByBlogIdAndParentIdIsNull(int blogId);

    int getCountByBlogId(int blogId);

    PageInfo<Comment> getAllComments(int currentPage);

    int deleteComment(int id);
}
