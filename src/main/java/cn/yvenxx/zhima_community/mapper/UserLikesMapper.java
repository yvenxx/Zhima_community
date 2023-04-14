package cn.yvenxx.zhima_community.mapper;

import cn.yvenxx.zhima_community.model.UserLikes;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserLikesMapper {
    List<UserLikes> getAll();

    int insert(UserLikes userLike);

    int update(UserLikes userLike);

    Page<UserLikes> selectAllByArticleId(String articleId);

    Page<UserLikes> selectAllByUserId(String likeUserId);

    UserLikes selectByUserIdAndArticleId(String articleId, String likeUserId);
}
