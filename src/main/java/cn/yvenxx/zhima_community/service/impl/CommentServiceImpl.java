package cn.yvenxx.zhima_community.service.impl;

import cn.yvenxx.zhima_community.mapper.CommentMapper;
import cn.yvenxx.zhima_community.model.Comment;
import cn.yvenxx.zhima_community.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public PageInfo<Comment> getCommentByBlogId(int currentPage,int blogId) {
        PageHelper.startPage(currentPage,20);
        PageInfo<Comment> commentPageInfo = new PageInfo<Comment>(commentMapper.getCommentByBlogId(blogId));

        return commentPageInfo;
    }

    @Override
    public int comment(Comment comment) {
        return commentMapper.comment(comment);
    }

    @Override
    public int getCountByBlogIdAndParentIdIsNull(int blogId) {
        return commentMapper.getCountByBlogIdAndParentIdIsNull(blogId);
    }

    @Override
    public int getCountByBlogId(int blogId) {
        return commentMapper.getCountByBlogId(blogId);
    }

}
