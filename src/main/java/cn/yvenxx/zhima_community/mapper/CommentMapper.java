package cn.yvenxx.zhima_community.mapper;

import cn.yvenxx.zhima_community.model.Comment;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    /**
     * 根据 文章id 查所有评论
     */
    Page<Comment> getCommentByBlogId(int blogId);

    int comment(Comment comment);

    int getCountByBlogIdAndParentIdIsNull(int blogId);

    int getCountByBlogId(int blogId);

    Page<Comment> getAllComments();

    int deleteCommentById(int id);

    int deleteCommentByBlogId(int blogId);
}
